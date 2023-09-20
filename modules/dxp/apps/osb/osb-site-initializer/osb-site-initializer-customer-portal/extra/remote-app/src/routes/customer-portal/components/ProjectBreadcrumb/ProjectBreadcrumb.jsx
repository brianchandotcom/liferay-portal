/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {memo, useCallback, useEffect, useState} from 'react';

// import {useAppPropertiesContext} from '~/common/contexts/AppPropertiesContext';

import {IconBreadcrumbs} from '~/common/icons';

// import {getAccounts} from '~/common/services/liferay/graphql/queries';

import i18n from '../../../../common/I18n';
import Skeleton from '../../../../common/components/Skeleton';
import useCurrentKoroneikiAccount from '../../../../common/hooks/useCurrentKoroneikiAccount';
import useDebounce from '../../../../common/hooks/useDebounce';
import useIntersectionObserver from '../../../../common/hooks/useIntersectionObserver';

import useKoroneikiAccounts from '../../../../common/hooks/useKoroneikiAccounts';

// import {getKoroneikiAccounts} from '../../../common/services/liferay/graphql/queries';

import {getTooltipContentRenderer} from '../../containers/ActivationKeysTable/utils/getTooltipContentRenderer';
import PopoverIcon from '../ActivationStatus/DXPCloud/components/PopoverIcon';

const DELAY_TYPING_TIME = 500;
const MAX_ITEM_BEFORE_FILTER = 5;

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
					placeholder={i18n.translate('search')}
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
						<ClayIcon symbol={isClear ? 'times' : 'search'} />
					</Button>
				</ClayInput.GroupInsetItem>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
});

const InfiniteScroll = memo(
	({koroneikiAccounts, selectedKoroneikiAccount, setPage}) => {
		const [items, setItems] = useState([]);
		const [isLoading, setIsLoading] = useState(false);
		const [error, setError] = useState(null);

		const fetchData = useCallback(async () => {
			// eslint-disable-next-line no-console
			console.log(`Called`);

			setIsLoading(true);
			setError(null);

			try {
				const data = koroneikiAccounts;

				setItems((prevItems) => {
					return [...prevItems, ...data];
				});

				setPage((prevPage) => {
					// eslint-disable-next-line no-console
					console.log('prevPage', prevPage);

					return prevPage + 1;
				});
			} catch (error) {
				setError(error);
			} finally {
				setIsLoading(false);
			}
		}, [koroneikiAccounts, setPage]);

		useEffect(() => {
			fetchData();
		}, []);

		// eslint-disable-next-line no-console
		console.log('items', items);

		const handleScroll = useCallback(() => {
			if (
				window.innerHeight + document.documentElement.scrollTop !==
					document.documentElement.offsetHeight ||
				isLoading
			) {
				return;
			}

			// eslint-disable-next-line no-console
			console.log('Fetching');

			fetchData();
		}, [fetchData, isLoading]);

		useEffect(() => {
			window.addEventListener('scroll', handleScroll);

			return () => window.removeEventListener('scroll', handleScroll);
			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, [isLoading]);

		const getHref = useCallback((accountKey) => {
			const hashLocation = window.location.hash.replace(
				/[A-Z]+-\d+/g,
				accountKey
			);

			return `${Liferay.ThemeDisplay.getLayoutURL()}/${hashLocation}`;
		}, []);

		const PROJECT_NAME_LIMIT = 16;
		const abbreviationProjectName = useCallback((selectedLabel) => {
			if (selectedLabel?.length > PROJECT_NAME_LIMIT) {
				const splittedProjectName = selectedLabel?.split(
					'',
					PROJECT_NAME_LIMIT
				);

				const truncateProjectName = splittedProjectName.join('');

				return (
					<ClayTooltipProvider
						contentRenderer={({title}) =>
							getTooltipContentRenderer(title)
						}
						delay={100}
					>
						<span>{truncateProjectName}...</span>
					</ClayTooltipProvider>
				);
			}

			return selectedLabel;
		}, []);

		const getDropDownItems = useCallback(
			() =>
				items?.map((koroneikiAccount, index) => {
					const isSelected =
						koroneikiAccount?.accountKey ===
						selectedKoroneikiAccount?.accountKey;

					return (
						<>
							<ClayDropDown.Item
								active={isSelected}
								className="align-items-center cp-breadcrumbs-dropdown-item d-flex font-weight-semi-bold pl-3 pr-5 text-paragraph"
								href={
									!isSelected
										? getHref(koroneikiAccount?.accountKey)
										: ''
								}
								key={`${koroneikiAccount.code}-${index}`}
								symbolRight={isSelected ? 'check' : ''}
							>
								<div>
									<IconBreadcrumbs height={25} width={25} />
								</div>

								<div
									className="pl-2"
									title={koroneikiAccount?.name}
								>
									{abbreviationProjectName(
										koroneikiAccount?.name
									)}
								</div>
							</ClayDropDown.Item>

							{isLoading && <p>Loading...</p>}

							{error && <p>Error: {error.message}</p>}
						</>
					);
				}),
			[
				items,
				selectedKoroneikiAccount?.accountKey,
				getHref,
				abbreviationProjectName,
				isLoading,
				error,
			]
		);

		return getDropDownItems();
	}
);

const Dropdown = memo(
	({
		fetching,
		initialTotalCount,
		koroneikiAccounts,
		onIntersecting,
		onSearch,
		page,
		searching,
		selectedKoroneikiAccount,
		setPage,
		totalCount,
	}) => {
		const [active, setActive] = useState(false);
		const [trackedRef, isIntersecting] = useIntersectionObserver();

		useEffect(() => {
			if (isIntersecting) {
				onIntersecting();
			}
		}, [isIntersecting, onIntersecting]);

		const PROJECT_NAME_LIMIT = 16;
		const abbreviationProjectName = useCallback((selectedLabel) => {
			if (selectedLabel?.length > PROJECT_NAME_LIMIT) {
				const splittedProjectName = selectedLabel?.split(
					'',
					PROJECT_NAME_LIMIT
				);

				const truncateProjectName = splittedProjectName.join('');

				return (
					<ClayTooltipProvider
						contentRenderer={({title}) =>
							getTooltipContentRenderer(title)
						}
						delay={100}
					>
						<span>{truncateProjectName}...</span>
					</ClayTooltipProvider>
				);
			}

			return selectedLabel;
		}, []);

		const dropdownProjectsExceeded =
			initialTotalCount > MAX_ITEM_BEFORE_FILTER;

		return (
			<ClayDropDown
				active={active}
				alignmentPosition={['br', 'tl']}
				closeOnClickOutside
				hasRightSymbols
				menuElementAttrs={{
					className: classNames('cp-project-breadcrumbs-menu p-0', {
						'cp-extended-dropdown': dropdownProjectsExceeded,
						'cp-short-dropdown': !dropdownProjectsExceeded,
					}),
				}}
				onActiveChange={setActive}
				trigger={
					<Button className="align-items-center bg-transparent cp-project-breadcrumbs-toggle d-flex p-0">
						<div
							className="font-weight-bold h5 m-0 text-neutral-9"
							title={selectedKoroneikiAccount?.name}
						>
							{abbreviationProjectName(
								selectedKoroneikiAccount?.name
							)}
						</div>

						<span className="inline-item-after position-absolute text-brand-primary">
							<ClayIcon
								symbol={active ? 'caret-top' : 'caret-bottom'}
							/>
						</span>
					</Button>
				}
			>
				{
					<div className="dropdown-section px-3">
						{dropdownProjectsExceeded && (
							<Search setSearchTerm={onSearch} />
						)}
					</div>
				}

				{searching && !koroneikiAccounts && (
					<ClayDropDown.Section className="px-3">
						<div className="font-weight-semi-bold text-neutral-5 text-paragraph-sm">
							{i18n.translate('loading')}
						</div>
					</ClayDropDown.Section>
				)}

				{!searching &&
					!koroneikiAccounts?.length &&
					initialTotalCount > 1 && (
						<div className="dropdown-section px-3">
							<div className="font-weight-semi-bold text-neutral-5 text-paragraph-sm">
								{i18n.translate('no-projects-match-that-name')}
							</div>
						</div>
					)}

				{!!koroneikiAccounts?.length && initialTotalCount > 1 && (
					<ClayDropDown.ItemList>
						<InfiniteScroll
							koroneikiAccounts={koroneikiAccounts}
							page={page}
							selectedKoroneikiAccount={selectedKoroneikiAccount}
							setPage={setPage}
						/>

						{koroneikiAccounts?.length < totalCount && !fetching && (
							<ClayDropDown.Section className="px-3">
								<div
									className="font-weight-semi-bold text-neutral-5 text-paragraph-sm"
									ref={trackedRef}
								>
									{i18n.translate('loading')}
								</div>
							</ClayDropDown.Section>
						)}
					</ClayDropDown.ItemList>
				)}
			</ClayDropDown>
		);
	}
);

const ProjectBreadcrumb = () => {
	const [initialTotalCount, setInitialTotalCount] = useState(0);
	const [projectStatus, setProjectStatus] = useState('');
	const [page, setPage] = useState(1);

	const {
		data: currentKoroneikiAccountData,
		loading: currentKoroneikiAccountLoading,
	} = useCurrentKoroneikiAccount();

	const selectedKoroneikiAccount =
		currentKoroneikiAccountData?.koroneikiAccountByExternalReferenceCode;

	const {
		data,
		fetchMore,
		fetching,
		loading,
		onSearch,
		searching,
	} = useKoroneikiAccounts({
		selectedFilterCategory: {
			filter: (searchBuilder) => searchBuilder,
			page: 1,
			pageSize: 5,
		},
	});

	useEffect(() => {
		if (data?.c.koroneikiAccounts.totalCount > initialTotalCount) {
			setInitialTotalCount(data.c.koroneikiAccounts.totalCount);
			setProjectStatus(selectedKoroneikiAccount?.status);
		}
	}, [
		data?.c.koroneikiAccounts.totalCount,
		initialTotalCount,
		selectedKoroneikiAccount?.status,
	]);

	if (currentKoroneikiAccountLoading || loading) {
		return <Skeleton height={30} width={264} />;
	}

	return (
		<div className="align-items-center bg-neutral-1 cp-breadcrumbs-container d-flex justify-content-between mb-3 p-3">
			<div>
				<IconBreadcrumbs />
			</div>

			<div className="cp-breadcrumbs-dropdown">
				<Dropdown
					fetching={fetching}
					initialTotalCount={initialTotalCount}
					koroneikiAccounts={data?.c.koroneikiAccounts.items}
					onIntersecting={() =>
						fetchMore({
							variables: {
								page: data?.c.koroneikiAccounts.page + 1,
							},
						})
					}
					onSearch={onSearch}
					page={page}
					searching={searching}
					selectedKoroneikiAccount={selectedKoroneikiAccount}
					setPage={setPage}
					totalCount={data?.c.koroneikiAccounts.totalCount}
				/>

				<div
					className={classNames('cp-breadcrumbs-popover', {
						[`cp-breadcrumbs-popover-${projectStatus?.toLowerCase()}`]: projectStatus,
					})}
				>
					<PopoverIcon
						symbol="simple-circle"
						title={i18n.translate(`${projectStatus}`)}
					/>

					{projectStatus && (
						<span className="cp-breadcrumbs-status text-paragraph-sm">
							{i18n.translate(`${projectStatus}`)}
						</span>
					)}
				</div>
			</div>
		</div>
	);
};

export default ProjectBreadcrumb;
