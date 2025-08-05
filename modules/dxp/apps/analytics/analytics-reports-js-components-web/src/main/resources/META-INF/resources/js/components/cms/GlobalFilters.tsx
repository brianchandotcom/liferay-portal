/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useEffect, useState} from 'react';

import {Context} from '../../Context';
import ApiHelper from '../../apis/ApiHelper';
import {buildQueryString} from '../../utils/buildQueryString';
import Filter from '../Filter';
import {
	RangeSelectors,
	RangeSelectorsDropdown,
} from '../RangeSelectorsDropdown';
import Title from '../Title';

const initialChannel = {
	label: Liferay.Language.get('all-channels'),
	value: 'ALL',
};

const PATH = '/o/analytics-cms-rest/v1.0/channels';

const fetchChannels = async (keywords: string = '') => {
	const queryParams = buildQueryString({
		keywords,
	});
	const endpoint = `${PATH}${queryParams}`;

	const {data, error} = await ApiHelper.get<{
		items: {groupId: string; name: string}[];
	}>(endpoint);

	if (data) {
		return data.items.map(({groupId, name}) => ({
			label: name,
			value: String(groupId),
		}));
	}

	if (error) {
		console.error(error);
	}

	return [];
};

const GlobalFilters = () => {
	const {changeChannelFilter, changeRangeSelectorFilter, filters} =
		useContext(Context);

	const [channels, setChannels] = useState([initialChannel]);
	const [loadingChannels, setLoadingChannels] = useState<boolean>(true);

	const triggerLabel =
		channels.find((channel) => channel.value === filters.channel)?.label ||
		Liferay.Language.get('all-channels');

	useEffect(() => {
		const fetchInitialData = async () => {
			setLoadingChannels(true);

			try {
				const fetchedChannels = await fetchChannels();
				setChannels([initialChannel, ...fetchedChannels]);
			}
			catch (error) {
				console.error('Failed to fetch channels:', error);
			}
			finally {
				setLoadingChannels(false);
			}
		};

		fetchInitialData();
	}, []);

	const searchChannels = async (value: string) => {
		try {
			const channels = await fetchChannels(value);

			setChannels(value ? channels : [initialChannel, ...channels]);
		}
		catch (error) {
			console.error('Failed to search channels:', error);
		}
		finally {
			setLoadingChannels(false);
		}
	};

	return (
		<div className="d-flex global-filters justify-content-between mb-3">
			<Title value={Liferay.Language.get('overview')} />

			<div className="d-flex">
				<Filter
					active={filters.channel}
					className="mr-3"
					filterByValue="channels"
					icon="sites"
					items={channels}
					loading={loadingChannels}
					onSearch={async (value) => {
						setLoadingChannels(true);

						searchChannels(value);
					}}
					onSelectItem={(item) => changeChannelFilter(item.value)}
					title={Liferay.Language.get('filter-by-channels')}
					triggerLabel={triggerLabel}
				/>

				<RangeSelectorsDropdown
					activeRangeSelector={filters.rangeSelector}
					availableRangeKeys={[
						RangeSelectors.Last7Days,
						RangeSelectors.Last30Days,
					]}
					onChange={changeRangeSelectorFilter}
					showDescription={false}
					showIcon={true}
					size="xs"
				/>
			</div>
		</div>
	);
};

export default GlobalFilters;
