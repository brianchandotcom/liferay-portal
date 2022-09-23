/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import Button from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {memo, useCallback, useEffect, useMemo, useState} from 'react';

const spritemap =
	Liferay.ThemeDisplay.getCDNBaseURL() +
	'/o/admin-theme/images/clay/icons.svg';

const DELAY_TYPING_TIME = 500;
const MAX_ITEM_BEFORE_FILTER = 9;
const FIRST_PAGE = 1;

const useIntersectionObserver = () => {
	const [trackedRefCurrent, setTrackedRefCurrent] = useState();
	const [isIntersecting, setIsIntersecting] = useState(false);

	const memoizedSetIntersecting = useCallback((entities) => {
		const target = entities[0];

		setIsIntersecting(target.isIntersecting);
	}, []);

	useEffect(() => {
		const observer = new IntersectionObserver(memoizedSetIntersecting, {
			root: null,
			threshold: 1.0,
		});

		if (trackedRefCurrent) {
			observer.observe(trackedRefCurrent);
		}

		return () => {
			if (trackedRefCurrent) {
				observer.unobserve(trackedRefCurrent);
			}
		};
	}, [memoizedSetIntersecting, trackedRefCurrent]);

	return [setTrackedRefCurrent, isIntersecting];
};

const useDebounce = (value, delay) => {
	const [debouncedValue, setDebouncedValue] = useState(value);

	useEffect(() => {
		const handler = setTimeout(() => {
			setDebouncedValue(value);
		}, delay);

		return () => {
			clearTimeout(handler);
		};
	}, [value, delay]);

	return debouncedValue;
};

const useCurrentKoroneikiAccount = () => {
	const [koroneikiAccount, setKoroneikiAccount] = useState();

	const accountKey = useMemo(() => {
		const hashLocation = window.location.hash;

		return hashLocation.replace('#/', '').split('/').filter(Boolean)[0];
	}, []);

	useEffect(() => {
		const getKoroneikiAccount = async (externalReferenceCode) => {
			// eslint-disable-next-line @liferay/portal/no-global-fetch
			const response = await fetch(
				`/o/c/koroneikiaccounts/by-external-reference-code/${externalReferenceCode}`,
				{
					headers: {
						'accept': 'application/json',
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			if (response.ok) {
				setKoroneikiAccount(await response.json());

				return;
			}

			Liferay.Util.openToast({
				message: 'An unexpected error occured.',
				type: 'danger',
			});
		};

		if (accountKey) {
			getKoroneikiAccount(accountKey);
		}
	}, [accountKey]);

	return koroneikiAccount;
};

const useSearchTerm = (onSearch) => {
	const [lastSearchTerm, setLastSearchTerm] = useState('');

	return (searchTerm) => {
		if (searchTerm !== lastSearchTerm) {
			onSearch(searchTerm);
			setLastSearchTerm(searchTerm);
		}
	};
};

const getKoroneikiAccounts = async (filter, page, onResponse) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`/o/c/koroneikiaccounts?page=${page}${filter}`,
		{
			headers: {
				'accept': 'application/json',
				'x-csrf-token': Liferay.authToken,
			},
		}
	);

	if (response.ok) {
		onResponse(await response.json());

		return;
	}

	Liferay.Util.openToast({
		message: 'An unexpected error occured.',
		type: 'danger',
	});
};

const useKoroneikiAccounts = () => {
	const [koroneikiAccounts, setKoroneikiAccounts] = useState();
	const [initialTotalCount, setInitialTotalCount] = useState(0);
	const [fetching, setFetching] = useState(false);
	const [currentQuery, setCurrentQuery] = useState({
		filter: '',
		page: FIRST_PAGE,
	});

	useEffect(() => {
		if (currentQuery.page !== FIRST_PAGE) {
			setFetching(true);
		}

		getKoroneikiAccounts(
			currentQuery.filter,
			currentQuery.page,
			(koroneikiAccounts) => {
				if (koroneikiAccounts) {
					setKoroneikiAccounts((previousKoroneikiAccounts) => {
						if (currentQuery.page !== FIRST_PAGE) {
							koroneikiAccounts.items = [
								...previousKoroneikiAccounts.items,
								...koroneikiAccounts.items,
							];

							return {
								...koroneikiAccounts,
							};
						}

						return koroneikiAccounts;
					});

					setFetching(false);
				}
			}
		);
	}, [currentQuery.filter, currentQuery.page]);

	useEffect(() => {
		if (koroneikiAccounts?.totalCount > initialTotalCount) {
			setInitialTotalCount(koroneikiAccounts.totalCount);
		}
	}, [initialTotalCount, koroneikiAccounts?.totalCount]);

	const fetchMore = () =>
		setCurrentQuery((previousCurrentQuery) => ({
			filter: previousCurrentQuery.filter,
			page: previousCurrentQuery.page + 1,
		}));

	const search = useSearchTerm((searchTerm) =>
		setCurrentQuery({
			filter: searchTerm && `&filter=contains(name, '${searchTerm}')`,
			page: FIRST_PAGE,
		})
	);

	return {
		fetchMore,
		fetching,
		initialTotalCount,
		koroneikiAccounts,
		search,
	};
};

const Search = memo(({setSearchTerm}) => {
	const [value, setValue] = useState('');
	const debouncedValue = useDebounce(value, DELAY_TYPING_TIME);

	const [isClear, setIsClear] = useState(false);

	useEffect(() => setIsClear(!!value), [value]);
	useEffect(() => setSearchTerm(debouncedValue), [
		debouncedValue,
		setSearchTerm,
	]);

	return (
		<ClayInput.Group className="m-0" small>
			<ClayInput.GroupItem>
				<ClayInput
					className="border-brand-primary-lighten-5 font-weight-semi-bold text-neutral-10 text-paragraph-sm"
					insetAfter
					onChange={(event) => setValue(event.target.value)}
					placeholder={Liferay.Language.get('search')}
					type="text"
					value={value}
				/>

				<ClayInput.GroupInsetItem
					after
					className="border-brand-primary-lighten-5"
					tag="span"
				>
					<Button
						displayType="unstyled"
						onClick={() =>
							setValue((previousValue) =>
								isClear ? '' : previousValue
							)
						}
					>
						<ClayIcon
							spritemap={spritemap}
							symbol={isClear ? 'times' : 'search'}
						/>
					</Button>
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
});

const AllProjectButton = memo(({onClick}) => {
	const [isHover, setIsHover] = useState(false);

	return (
		<a
			className="align-items-center d-flex dropdown-item font-weight-semi-bold pl-3 pr-5 py-1 text-decoration-none text-paragraph-sm"
			href={Liferay.ThemeDisplay.getCanonicalURL().replace(
				'/project',
				''
			)}
			onClick={onClick}
			onMouseEnter={() => setIsHover(true)}
			onMouseLeave={() => setIsHover(false)}
		>
			<span
				className={classNames('inline-item inline-item-before', {
					'invisible ml-n3': !isHover,
				})}
			>
				<ClayIcon spritemap={spritemap} symbol="angle-left" />
			</span>

			{Liferay.Language.get('all-projects')}
		</a>
	);
});

const DropDown = memo(
	({
		fetching,
		initialTotalCount,
		koroneikiAccounts,
		onIntersecting,
		onSearch,
		selectedKoroneikiAccount,
		totalCount,
	}) => {
		const [active, setActive] = useState(false);

		const [trackedRef, isIntersecting] = useIntersectionObserver();

		useEffect(() => {
			if (isIntersecting) {
				onIntersecting();
			}
		}, [isIntersecting, onIntersecting]);

		const getHref = useCallback((accountKey) => {
			const hashLocation = window.location.hash.replace(
				/[A-Z]+-\d+/g,
				accountKey
			);

			return `${Liferay.ThemeDisplay.getLayoutURL()}/${hashLocation}`;
		}, []);

		const getDropDownItems = useCallback(
			() =>
				koroneikiAccounts?.map((koroneikiAccount, index) => {
					const isSelected =
						koroneikiAccount.accountKey ===
						selectedKoroneikiAccount?.accountKey;

					return (
						<ClayDropDown.Item
							active={isSelected}
							className="align-items-center d-flex font-weight-semi-bold pl-3 pr-5 py-1 text-paragraph-sm"
							href={
								!isSelected
									? getHref(koroneikiAccount.accountKey)
									: ''
							}
							key={`${koroneikiAccount.code}-${index}`}
							spritemap={spritemap}
							symbolRight={isSelected ? 'check' : ''}
						>
							{koroneikiAccount.name}
						</ClayDropDown.Item>
					);
				}),
			[getHref, koroneikiAccounts, selectedKoroneikiAccount?.accountKey]
		);

		return (
			<ClayDropDown
				active={active}
				alignmentPosition={['tl', 'br']}
				closeOnClickOutside
				hasRightSymbols
				menuElementAttrs={{
					className: 'cp-project-breadcrumbs-menu p-0',
				}}
				onActiveChange={setActive}
				trigger={
					<Button className="align-items-center bg-white cp-project-breadcrumbs-toggle d-flex p-0">
						<div className="font-weight-bold h5 m-0 text-neutral-9">
							{selectedKoroneikiAccount.name}
						</div>

						<span className="inline-item inline-item-after position-absolute text-brand-primary">
							<ClayIcon
								spritemap={spritemap}
								symbol="caret-bottom"
							/>
						</span>
					</Button>
				}
			>
				{initialTotalCount > MAX_ITEM_BEFORE_FILTER && (
					<div className="dropdown-section px-3">
						<Search setSearchTerm={onSearch} />
					</div>
				)}

				{!koroneikiAccounts.length && initialTotalCount > 1 && (
					<div className="dropdown-section px-3">
						<div className="font-weight-semi-bold text-neutral-5 text-paragraph-sm">
							{Liferay.Language.get(
								'no-projects-match-that-name'
							)}
						</div>
					</div>
				)}

				{!!koroneikiAccounts.length && initialTotalCount > 1 && (
					<ClayDropDown.ItemList className="overflow-auto">
						{getDropDownItems()}

						{koroneikiAccounts.length < totalCount && !fetching && (
							<ClayDropDown.Section className="px-3">
								<div
									className="font-weight-semi-bold text-neutral-5 text-paragraph-sm"
									ref={trackedRef}
								>
									{Liferay.Language.get('loading')}
								</div>
							</ClayDropDown.Section>
						)}
					</ClayDropDown.ItemList>
				)}

				<AllProjectButton onClick={() => setActive(false)} />
			</ClayDropDown>
		);
	}
);

export default function () {
	const selectedKoroneikiAccount = useCurrentKoroneikiAccount();
	const {
		fetchMore,
		fetching,
		initialTotalCount,
		koroneikiAccounts,
		search,
	} = useKoroneikiAccounts();

	if (!koroneikiAccounts || !selectedKoroneikiAccount) {
		return (
			<span
				className="cp-project-breadcrumbs-skeleton"
				style={{height: '30px', width: '264px'}}
			></span>
		);
	}

	return (
		<DropDown
			fetching={fetching}
			initialTotalCount={initialTotalCount}
			koroneikiAccounts={koroneikiAccounts.items}
			onIntersecting={fetchMore}
			onSearch={search}
			selectedKoroneikiAccount={selectedKoroneikiAccount}
			totalCount={koroneikiAccounts.totalCount}
		/>
	);
}
