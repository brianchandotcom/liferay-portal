import ExperimentListTitle from '../components/ExperimentListTitle';
import Label from 'shared/components/Label';
import moment from 'moment';
import momentTimezone from 'moment-timezone';
import React from 'react';
import {applyTimeZone, formatDateToTimeZone} from 'shared/util/date';
import {DateCell} from 'shared/components/table/cell-components';
import {getStatusColor, getStatusName} from 'experiments/util/experiments';
import {isNil} from 'lodash';
import {TableDataCell} from 'shared/components/table/cell-components';

export default timeZoneId => [
	{
		accessor: 'name',
		cellRenderer: ({data: {id, name, pageURL}}) => (
			<ExperimentListTitle id={id} title={name} touchpoint={pageURL} />
		),
		className: 'table-cell-expand',
		label: Liferay.Language.get('name')
	},
	{
		accessor: 'pageURL',
		cellRenderer: ({data: {pageURL}}) => (
			<TableDataCell firstColumn={false} title={pageURL} />
		),
		className: 'table-cell-expand',
		label: Liferay.Language.get('url').toUpperCase(),
		width: '300px'
	},
	{
		accessor: 'status',
		dataFormatter: value => (
			<Label
				className='experiment-status'
				display={getStatusColor(value)}
				key='status'
				size='lg'
			>
				{getStatusName(value)}
			</Label>
		),
		label: Liferay.Language.get('status')
	},
	{
		accessor: 'type',
		dataFormatter: value => (value === 'AB' ? 'A/B' : value),
		label: Liferay.Language.get('type')
	},
	{
		accessor: 'createDate',
		cellRenderer: DateCell,
		cellRendererProps: {
			dateFormatter: date => formatDateToTimeZone(date, 'll', timeZoneId),
			datePath: 'createDate'
		},
		className: 'table-column-text-end',
		label: Liferay.Language.get('created')
	},
	{
		accessor: 'modifiedDate',
		cellRenderer: DateCell,
		cellRendererProps: {
			dateFormatter: modifiedDate => {
				if (!isNil(modifiedDate)) {
					const timeZonedDate = applyTimeZone(
						modifiedDate,
						timeZoneId
					);

					return momentTimezone()
						.tz(timeZoneId)
						.diff(timeZonedDate, 'day') > 0
						? formatDateToTimeZone(modifiedDate, 'll', timeZoneId)
						: moment.utc(modifiedDate).fromNow();
				}
			},
			datePath: 'modifiedDate'
		},
		className: 'table-column-text-end',
		label: Liferay.Language.get('last-modified')
	}
];
