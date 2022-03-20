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

import ClayDropDown from '@clayui/drop-down';
import React, {useCallback, useEffect, useState} from 'react';

const spritemap =
	Liferay.ThemeDisplay.getCDNBaseURL() +
	'/o/admin-theme/images/clay/icons.svg';

const KORONEIKI_ACCOUNTS_EVENT_NAME =
	'customer-portal-koroneiki-accounts-available';

const SELECTED_KORONEIKI_ACCOUNT_EVENT_NAME = 'customer-portal-project-loading';

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

const eventFetchMoreData = Liferay.publish(
	'customer-portal-fetch-more-koroneiki-accounts'
);

const eventSearchData = Liferay.publish(
	'customer-portal-search-koroneiki-accounts'
);

export default function () {
	const [active, setActive] = useState(false);
	const [searchTerm, setSearchTerm] = useState('');
	const debouncedSearchTerm = useDebounce(searchTerm, 500);

	const [trackedRef, isIntersecting] = useIntersectionObserver();

	const [koroneikiAccounts, setKoroneikiAccounts] = useState([]);
	const [totalCount, setTotalCount] = useState();
	const [initialTotalCount, setInitialTotalCount] = useState();
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
						href={
							isSelected
								? ''
								: getHref(koroneikiAccount.accountKey)
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

	if (!koroneikiAccounts || !selectedKoroneikiAccount) {
		return <div>Loading</div>;
	}

	return (
		<ClayDropDown
			active={active}
			closeOnClickOutside
			hasRightSymbols
			onActiveChange={setActive}
			trigger={<button className="btn btn-primary">Click here!</button>}
		>
			{initialTotalCount > 20 && (
				<ClayDropDown.Search
					onChange={(event) => setSearchTerm(event.target.value)}
					spritemap={spritemap}
					value={searchTerm}
				/>
			)}

			<ClayDropDown.ItemList>
				{getDropDownItems()}

				{!!koroneikiAccounts.length &&
					koroneikiAccounts.length < totalCount && (
						<ClayDropDown.Section>
							<div ref={trackedRef}>Loading more...</div>
						</ClayDropDown.Section>
					)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}
