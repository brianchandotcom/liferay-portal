import * as API from 'shared/api';
import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import Loading, {Align} from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useState} from 'react';
import URLConstants from 'shared/util/url-constants';
import {
	createOrderIOMap,
	getDefaultSortOrder,
	NAME
} from 'shared/util/pagination';
import {Routes, SEGMENTS, toRoute} from 'shared/util/router';
import {sub} from 'shared/util/lang';
import {useParams} from 'react-router-dom';
import {useQueryPagination, useRequest} from 'shared/hooks';

type Item = {
	children: Item[];
	id: string;
	name: string;
};

interface IFilterBySegment {
	onFilterChange: (item: Item | null) => void;
}

const filterBySegment: React.FC<IFilterBySegment> = ({onFilterChange}) => {
	const {channelId, groupId} = useParams();
	const {delta, orderIOMap, page, query} = useQueryPagination({
		initialOrderIOMap: createOrderIOMap(NAME, getDefaultSortOrder(NAME))
	});
	const [selectedItem, setSelectedItem] = useState(null);

	const {data, loading} = useRequest({
		dataSourceFn: API.individualSegment.search,
		variables: {
			channelId,
			delta,
			groupId,
			orderIOMap,
			page,
			query
		}
	});

	return (
		<div className='d-flex justify-content-between w-100 analytics-segment-filter-root'>
			<div className='align-items-center d-flex'>
				<ClayDropDown
					trigger={
						<ClayButton
							borderless
							disabled={loading}
							displayType='secondary'
							size='sm'
						>
							{loading && <Loading align={Align.Left} />}

							{Liferay.Language.get('filter')}

							<ClayIcon className='ml-2' symbol='caret-bottom' />
						</ClayButton>
					}
				>
					<ClayDropDown.Search
						placeholder={Liferay.Language.get('search')}
					/>

					<ClayDropDown.ItemList
						items={[
							{
								children: data?.items ?? [],
								id: 1,
								name: sub(Liferay.Language.get('filter-by-x'), [
									Liferay.Language.get('segment')
								])
							}
						]}
					>
						{(item: Item) => (
							<ClayDropDown.Group
								header={item.name}
								items={item.children}
								key={item.name}
							>
								{(item: Item) => (
									<ClayDropDown.Item
										key={item.name}
										onClick={() => {
											setSelectedItem(item);

											onFilterChange(item);
										}}
									>
										{item.name}
									</ClayDropDown.Item>
								)}
							</ClayDropDown.Group>
						)}
					</ClayDropDown.ItemList>

					{!data?.items.length && (
						<NoResultsDisplay
							description={
								<>
									<div
										className='d-flex flex-column justify-content-center'
										style={{minHeight: 240}}
									>
										<h4 className='no-results-title'>
											{Liferay.Language.get(
												'there-are-no-segments'
											)}
										</h4>

										{Liferay.Language.get(
											'start-by-creating-a-segment'
										)}

										<a
											className='d-block mb-3'
											href={
												URLConstants.SegmentsDocumentationLink
											}
											key='DOCUMENTATION'
											target='_blank'
										>
											{Liferay.Language.get(
												'learn-more-about-segments'
											)}
										</a>
									</div>

									<ClayLink
										button
										className='button-root'
										displayType='primary'
										href={toRoute(
											Routes.CONTACTS_LIST_SEGMENT,
											{
												channelId,
												groupId,
												type: SEGMENTS
											}
										)}
									>
										{Liferay.Language.get('create-segment')}
									</ClayLink>
								</>
							}
							title={null}
						/>
					)}
				</ClayDropDown>

				{selectedItem && (
					<ClayLabel
						className='ml-2'
						closeButtonProps={{
							'aria-label': Liferay.Language.get('close'),
							id: 'closeId',
							title: Liferay.Language.get('close')
						}}
						large
						onClick={() => {
							setSelectedItem(null);
							onFilterChange(null);
						}}
					>
						{selectedItem.name}
					</ClayLabel>
				)}
			</div>

			{selectedItem && (
				<div className='d-flex'>
					<ClayButton
						borderless
						data-tooltip
						data-tooltip-align='top'
						displayType='secondary'
						onClick={() => {
							setSelectedItem(null);
							onFilterChange(null);
						}}
						size='sm'
						title={Liferay.Language.get('remove-filter')}
					>
						<ClayIcon symbol='times-circle' />
					</ClayButton>

					<div className='divider' />
				</div>
			)}
		</div>
	);
};

export default filterBySegment;
