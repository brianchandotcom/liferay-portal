/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';
import React, {
	createContext,
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useRef,
	useState,
} from 'react';

import {DEFAULT_FETCH_HEADERS, FDS_DEFAULT_PROPS} from './utils/constants';
import getDataSetResourceURL from './utils/getDataSetResourceURL';
import getDataSetSnapshotResourceURL from './utils/getDataSetSnapshotResourceURL';

import '../css/DataSets.scss';

interface IManageUserViewsProps {
	currentURL: string;
	namespace: string;
	portletId: string;
	systemDataSetEntries: Array<{name: string; title: string}>;
}

interface IDataSetLabelsContextValue {
	dataSetLabels: Record<string, string>;
	requestLabel: (fdsName: string) => void;
	systemDataSetNamesSet: Set<string>;
}

const DataSetLabelsContext = createContext<IDataSetLabelsContextValue | null>(
	null
);

const views = {
	contentRenderer: 'table',
	label: Liferay.Language.get('table'),
	name: 'table',
	schema: {
		fields: [
			{
				fieldName: 'label',
				label: Liferay.Language.get('user-view-name'),
			},
			{
				fieldName: 'creator.name',
				label: Liferay.Language.get('created-by'),
			},
			{
				contentRenderer: 'dataSetNameRenderer',
				fieldName: 'dataSetName',
				label: Liferay.Language.get('data-set'),
			},
			{
				contentRenderer: 'dataSetTypeRenderer',
				fieldName: 'dataSetType',
				label: Liferay.Language.get('data-set-type'),
			},
			{
				contentRenderer: 'dateTime',
				fieldName: 'dateModified',
				label: Liferay.Language.get('modified-date'),
			},
		],
	},
};

const DataSetNameRenderer = ({itemData}: {itemData: {fdsName?: string}}) => {
	const context = useContext(DataSetLabelsContext);
	const fdsName = itemData.fdsName || '';
	const cachedLabel =
		fdsName && context ? context.dataSetLabels[fdsName] : undefined;

	useEffect(() => {
		if (!context || !fdsName || cachedLabel !== undefined) {
			return;
		}

		context.requestLabel(fdsName);
	}, [cachedLabel, context, fdsName]);

	if (!fdsName) {
		return '';
	}

	if (cachedLabel === undefined) {
		return fdsName;
	}

	return cachedLabel || fdsName;
};

const DataSetTypeRenderer = ({itemData}: {itemData: {fdsName?: string}}) => {
	const context = useContext(DataSetLabelsContext);
	const fdsName = itemData.fdsName || '';
	const isSystem =
		!!fdsName && context
			? context.systemDataSetNamesSet.has(fdsName)
			: false;
	const dataSetType = isSystem ? 'system' : 'custom';
	const displayType = isSystem ? 'info' : 'warning';

	return (
		<ClayLabel displayType={displayType}>
			{Liferay.Language.get(dataSetType)}
		</ClayLabel>
	);
};

export default function ManageUserViews({
	currentURL,
	namespace,
	portletId,
	systemDataSetEntries = [],
}: IManageUserViewsProps) {
	const apiURL = getDataSetSnapshotResourceURL();
	const initialSystemDataSetLabels = useMemo(() => {
		return systemDataSetEntries.reduce<Record<string, string>>(
			(accumulator, entry) => {
				if (entry?.name && entry?.title) {
					accumulator[entry.name] = entry.title;
				}

				return accumulator;
			},
			{}
		);
	}, [systemDataSetEntries]);
	const [dataSetLabels, setDataSetLabels] = useState<Record<string, string>>(
		() => initialSystemDataSetLabels
	);
	const labelRequestsRef = useRef<Set<string>>(new Set());
	const systemDataSetNamesSet = useMemo(
		() => new Set(systemDataSetEntries.map((entry) => entry.name)),
		[systemDataSetEntries]
	);
	
        const filters = useMemo(() => [
                {
                        apiURL: '/o/headless-admin-user/v1.0/user-accounts?sort=name:asc',
                        autocompleteEnabled: true,
                        entityFieldType: 'integer',
                        id: 'creatorId',
                        inputPlaceholder: Liferay.Language.get('search'),
                        itemKey: 'id',
                        itemLabel: 'name',
                        items: [],
                        label: Liferay.Language.get('created-by'),
                        multiple: false,
                        type: 'selection',
                },
                {
                        apiURL: getUserViewsDataSetsURL,
                        autocompleteEnabled: true,
                        entityFieldType: 'string',
                        id: 'fdsName',
                        inputPlaceholder: Liferay.Language.get('search'),
                        itemKey: 'value',
                        itemLabel: 'label',
                        items: [],
                        label: Liferay.Language.get('data-set'),
                        multiple: false,
                        type: 'selection',
                },
                {
                        entityFieldType: 'date-time',
                        id: 'dateModified',
                        label: Liferay.Language.get('date'),
                        type: 'dateRange',
                },
        ], [getUserViewsDataSetsURL]);

        const systemDataSetNamesSet = useMemo(
                () => new Set(systemDataSetEntries.map((entry) => entry.name)),
                [systemDataSetEntries]
        );
        