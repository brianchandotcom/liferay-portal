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

import ClayButton from '@clayui/button';

import Table from './components/Table';
import getMDFListColumns from './components/utils/getMDFListColumns';
import useGetMDFRequestListItems from './hooks/useGetMDFRequestListItems';

interface MDFRequestListItem {
	activityPeriod?: string;
	id: string;
	totalCostOfExpense?: string;
	totalRequested?: string;
}

const MDFRequestList = () => {
	const {data: listItems} = useGetMDFRequestListItems();

	return (
		<div className="border-0 pb-3 pt-5 px-6 sheet">
			<h1>MDF Requests</h1>

			<div className="bg-neutral-1 p-3 rounded">
				<ClayButton className="mr-1" displayType="secondary">
					Export MDF Report
				</ClayButton>

				<ClayButton>New Request</ClayButton>
			</div>

			{listItems && (
				<div className="mt-3">
					<Table<MDFRequestListItem>
						borderless
						columns={getMDFListColumns()}
						responsive
						rows={listItems}
					/>
				</div>
			)}
		</div>
	);
};
export default MDFRequestList;
