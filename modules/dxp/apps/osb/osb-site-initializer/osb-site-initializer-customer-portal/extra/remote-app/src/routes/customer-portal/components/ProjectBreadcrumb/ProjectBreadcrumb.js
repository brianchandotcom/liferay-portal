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

import {Button} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {memo, useCallback, useEffect, useState} from 'react';
import i18n from '../../../../common/I18n';
import Skeleton from '../../../../common/components/Skeleton';
import useCurrentKoroneikiAccount from '../../../../common/hooks/useCurrentKoroneikiAccount';
import useDebounce from '../../../../common/hooks/useDebounce';
import useIntersectionObserver from '../../../../common/hooks/useIntersectionObserver';
import useKoroneikiAccounts from '../../../../common/hooks/useKoroneikiAccounts';

const DELAY_TYPING_TIME = 500;
const MAX_ITEM_BEFORE_FILTER = 9;

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
				<ClayIcon symbol="angle-left" />
			</span>

			{i18n.translate('all-projects')}
		</a>
	);
});

const Dropdown = memo(
	({
		fetching,
		initialTotalCount,
		koroneikiAccounts,
		onIntersecting,
		onSearch,
		searching,
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
							<ClayIcon symbol="caret-bottom" />
						</span>
					</Button>
				}
			>
				{initialTotalCount > MAX_ITEM_BEFORE_FILTER && (
					<div className="dropdown-section px-3">
						<Search setSearchTerm={onSearch} />
					</div>
				)}

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
					<ClayDropDown.ItemList className="overflow-auto">
						{getDropDownItems()}

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

				<AllProjectButton onClick={() => setActive(false)} />
			</ClayDropDown>
		);
	}
);

const ProjectBreadcrumb = () => {
	const [initialTotalCount, setInitialTotalCount] = useState(0);

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
		search,
		searching,
	} = useKoroneikiAccounts();

	useEffect(() => {
		if (data?.c.koroneikiAccounts.totalCount > initialTotalCount) {
			setInitialTotalCount(data.c.koroneikiAccounts.totalCount);
		}
	}, [data?.c.koroneikiAccounts.totalCount, initialTotalCount]);

	if (currentKoroneikiAccountLoading || loading) {
		return <Skeleton height={30} width={264} />;
	}

	return (
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
			onSearch={search}
			searching={searching}
			selectedKoroneikiAccount={selectedKoroneikiAccount}
			totalCount={data?.c.koroneikiAccounts.totalCount}
		/>
	);
};

export default ProjectBreadcrumb;
