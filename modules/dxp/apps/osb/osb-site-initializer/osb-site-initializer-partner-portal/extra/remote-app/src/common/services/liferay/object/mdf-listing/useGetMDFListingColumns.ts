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

import useSWR from 'swr';

import {Liferay} from '../..';
import {MDFColumnKey} from '../../../../enums/mdfColumnKey';
import {MDFRequestListItem} from '../../../../interfaces/mdfRequestListItem';
import TableColumn from '../../../../interfaces/tableColumn';
import {LiferayAPIs} from '../../common/enums/apis';
import LiferayItems from '../../common/interfaces/liferayItems';
import liferayFetcher from '../../common/utils/fetcher';

interface MDFListingColumn {
	externalReferenceCode: MDFColumnKey;
	label: string;
	render?: () => JSX.Element;
}

export default function useGetMDFListingColumns() {
	const swr = useSWR(
		[`/o/${LiferayAPIs.OBJECT}/mdflistingcolumns`, Liferay.authToken],
		(url, token) =>
			liferayFetcher<LiferayItems<MDFListingColumn[]>>(url, token)
	);

	return {
		...swr,
		data: swr.data?.items.map<TableColumn<MDFRequestListItem>>((item) => ({
			columnKey: item.externalReferenceCode,
			label: item.label,
		})),
	};
}
