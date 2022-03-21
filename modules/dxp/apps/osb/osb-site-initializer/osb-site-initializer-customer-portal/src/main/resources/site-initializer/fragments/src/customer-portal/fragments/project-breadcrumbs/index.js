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
import React, {memo, useCallback, useEffect, useState} from 'react';

const spritemap =
	Liferay.ThemeDisplay.getCDNBaseURL() +
	'/o/admin-theme/images/clay/icons.svg';

const KORONEIKI_ACCOUNTS_EVENT_NAME =
	'customer-portal-koroneiki-accounts-available';

const SELECTED_KORONEIKI_ACCOUNT_EVENT_NAME = 'customer-portal-project-loading';

const eventFetchMoreData = Liferay.publish(
	'customer-portal-fetch-more-koroneiki-accounts'
);

const eventSearchData = Liferay.publish(
	'customer-portal-search-koroneiki-accounts'
);

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
			threshold: 0.25,
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

const Search = memo(({setSearchTerm}) => {
	const [value, setValue] = useState('');
	const [isClear, setIsClear] = useState(false);

	useEffect(() => setIsClear(!!value), [value]);
	useEffect(() => setSearchTerm(value), [setSearchTerm, value]);

	return (
		<ClayInput.Group className="m-0" small>
			<ClayInput.GroupItem>
				<ClayInput
					className="border-brand-primary-lighten-5 font-weight-semi-bold text-neutral-10 text-paragraph-sm"
					insetAfter
					onChange={(event) => setValue(event.target.value)}
					placeholder="Search"
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
			All Projects
		</a>
	);
});

const DropDown = memo(
	({
		initialTotalCount,
		koroneikiAccounts,
		selectedKoroneikiAccount,
		totalCount,
	}) => {
		const [active, setActive] = useState(false);

		const [searchTerm, setSearchTerm] = useState('');
		const debouncedSearchTerm = useDebounce(searchTerm, 500);

		const [trackedRef, isIntersecting] = useIntersectionObserver();

		useEffect(() => {
			if (isIntersecting) {
				eventFetchMoreData.fire();
			}
		}, [isIntersecting]);

		useEffect(() => {
			eventSearchData.fire({
				detail: debouncedSearchTerm,
			});
		}, [debouncedSearchTerm]);

		const getHref = useCallback((accountKey) => {
			const hashLocation = window.location.hash.replace(
				/[A-Z]+-\d+/g,
				accountKey
			);

			return `${Liferay.ThemeDisplay.getCanonicalURL()}/${hashLocation}`;
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
								!isSelected &&
								getHref(koroneikiAccount.accountKey)
							}
							key={`${koroneikiAccount.code}-${index}`}
							spritemap={spritemap}
							symbolRight={isSelected ? 'check' : ''}
						>
							{koroneikiAccount.name || koroneikiAccount.code}
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
							{selectedKoroneikiAccount.name ||
								selectedKoroneikiAccount.code}
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
				{initialTotalCount > 10 && (
					<div className="dropdown-section px-3">
						<Search setSearchTerm={setSearchTerm} />
					</div>
				)}

				{!koroneikiAccounts.length && (
					<div className="dropdown-section px-3">
						<div className="font-weight-semi-bold text-neutral-5 text-paragraph-sm">
							No projects match that name.
						</div>
					</div>
				)}

				{!!koroneikiAccounts.length && initialTotalCount > 1 && (
					<ClayDropDown.ItemList className="overflow-auto">
						{getDropDownItems()}

						{koroneikiAccounts.length < totalCount && (
							<ClayDropDown.Section className="px-3">
								<div
									className="font-weight-semi-bold text-neutral-5 text-paragraph-sm"
									ref={trackedRef}
								>
									Loading more...
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
	const [totalCount, setTotalCount] = useState();
	const [initialTotalCount, setInitialTotalCount] = useState();
	const [koroneikiAccounts, setKoroneikiAccounts] = useState();
	const [selectedKoroneikiAccount, setSelectKoroneikiAccount] = useState();

	useEffect(() => {
		Liferay.once(SELECTED_KORONEIKI_ACCOUNT_EVENT_NAME, ({detail}) => {
			setSelectKoroneikiAccount(detail);
		});

		return () => Liferay.detach(SELECTED_KORONEIKI_ACCOUNT_EVENT_NAME);
	}, []);

	useEffect(() => {
		Liferay.on(KORONEIKI_ACCOUNTS_EVENT_NAME, ({detail}) => {
			if (detail?.koroneikiAccounts) {
				setKoroneikiAccounts(detail.koroneikiAccounts);
			}

			if (detail?.totalCount) {
				setTotalCount(detail.totalCount);
			}

			if (detail?.initialTotalCount) {
				setInitialTotalCount(detail.initialTotalCount);
			}
		});

		return () => Liferay.detach(KORONEIKI_ACCOUNTS_EVENT_NAME);
	}, []);

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
			initialTotalCount={initialTotalCount}
			koroneikiAccounts={koroneikiAccounts}
			selectedKoroneikiAccount={selectedKoroneikiAccount}
			totalCount={totalCount}
		/>
	);
}
