import autobind from 'autobind-decorator';
import ClayButton from '@clayui/button';
import React from 'react';
import RowActions from 'shared/components/RowActions';
import Sticker from 'shared/components/Sticker';
import Table from 'shared/components/table';
import {DateCell, NameCell} from 'shared/components/table/cell-components';
import {mockIndividual} from 'test/data';
import {Routes} from 'shared/util/router';
import {times} from 'lodash';

const INDIVIDUALS = times(5, i =>
	mockIndividual(i, {
		age: Math.round(Math.random() * 100),
		country: 'USA',
		jobTitle: 'Developer'
	})
);

const INDIVIDUALS_EUROPE_ARCHITECT = times(5, i =>
	mockIndividual(i, {
		age: Math.round(Math.random() * 100),
		country: 'Europe',
		jobTitle: 'Architect'
	})
);

const INDIVIDUALS_EUROPE_WRITER = times(5, i =>
	mockIndividual(i, {
		age: Math.round(Math.random() * 100),
		country: 'Europe',
		jobTitle: 'Writer'
	})
);

const GROUPS = [
	{
		jobTitles: [
			{
				individuals: INDIVIDUALS,
				total: 5,
				value: 'Developer'
			}
		],
		total: 5,
		totalSalary: 250000,
		value: 'USA'
	},
	{
		jobTitles: [
			{
				individuals: INDIVIDUALS_EUROPE_ARCHITECT,
				total: 5,
				value: 'Architect'
			},
			{
				individuals: INDIVIDUALS_EUROPE_WRITER,
				total: 5,
				value: 'Writer'
			}
		],
		total: 10,
		totalSalary: 500000,
		value: 'Europe'
	}
];

const NameCellRenderIcon = () => <Sticker display='success' symbol='check' />;

const RowActionsCell = () => (
	<RowActions
		actions={[{label: 'edit'}, {label: 'delete'}]}
		quickActions={[
			{iconSymbol: 'pencil', label: 'edit'},
			{iconSymbol: 'trash', label: 'delete'}
		]}
	/>
);

class CustomRenderer extends React.Component {
	render() {
		const {name, properties} = this.props.data;

		return (
			<td
				className={`table-cell-expand${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				<h4 className='table-title'>{name}</h4>

				{properties.jobTitle}
			</td>
		);
	}
}

class CustomButtonCell extends React.Component {
	@autobind
	handleClick() {
		alert(JSON.stringify(this.props.data));
	}

	render() {
		return (
			<td
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<ClayButton
					className='button-root'
					displayType='secondary'
					onClick={this.handleClick}
				>
					{'Save as Segment'}
				</ClayButton>
			</td>
		);
	}
}

class TableKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<h3>{'Table'}</h3>

				<Table
					columns={[
						{
							accessor: 'name',
							className: 'table-cell-expand',
							label: 'Name',
							title: true
						},
						{
							accessor: 'properties.salary',
							label: 'Salary'
						},
						{
							accessor: 'properties.jobTitle',
							label: 'Job Title'
						},
						{
							accessor: 'properties.country',
							label: 'Country'
						},
						{
							accessor: 'properties.age',
							label: 'Age'
						}
					]}
					defaultSort={{
						field: 'name'
					}}
					items={INDIVIDUALS}
					rowIdentifier='id'
				/>

				<h3>{'Table Bordered'}</h3>

				<Table
					bordered
					columns={[
						{
							accessor: 'name',
							className: 'table-cell-expand',
							label: 'Name',
							title: true
						},
						{
							accessor: 'properties.salary',
							label: 'Salary'
						},
						{
							accessor: 'properties.jobTitle',
							label: 'Job Title'
						},
						{
							accessor: 'properties.country',
							label: 'Country'
						},
						{
							accessor: 'properties.age',
							label: 'Age'
						}
					]}
					defaultSort={{
						field: 'name'
					}}
					items={INDIVIDUALS}
					rowIdentifier='id'
				/>

				<h3>{'Custom Cell Renderer'}</h3>

				<Table
					columns={[
						{
							accessor: 'name',
							cellRenderer: CustomRenderer,
							label: 'Name',
							title: true
						},
						{
							accessor: 'properties.salary',
							label: 'Salary'
						},
						{
							accessor: 'properties.country',
							label: 'Country'
						},
						{
							accessor: 'properties.age',
							label: 'Age'
						},
						{
							cellRenderer: CustomButtonCell,
							sortable: false
						}
					]}
					defaultSort={{
						field: 'name'
					}}
					items={INDIVIDUALS}
					rowIdentifier='id'
				/>

				<h3>{'Nested Tables'}</h3>

				<h5>{'With default sorts and salary sort disabled'}</h5>

				<Table
					columns={[
						{
							accessor: 'value',
							className: 'table-cell-expand',
							label: 'Region',
							title: true
						},
						{
							accessor: 'total',
							className: 'table-cell-expand',
							label: 'Individual Count'
						},
						{
							accessor: 'totalSalary',
							className: 'table-cell-expand',
							label: 'Salary',
							sortable: false
						}
					]}
					defaultSort={{
						field: 'value',
						sortOrder: 'desc'
					}}
					headingNowrap
					items={GROUPS}
					nestedTables={[
						{
							accessor: 'jobTitles',
							columns: [
								{
									accessor: 'value',
									className: 'table-cell-expand',
									label: 'Job Title',
									title: true
								},
								{
									accessor: 'total',
									className: 'table-cell-expand',
									label: 'Individual Count'
								}
							],
							defaultSort: {
								field: 'total',
								sortOrder: 'desc'
							},
							rowIdentifier: 'value'
						},
						{
							accessor: 'individuals',
							columns: [
								{
									accessor: 'name',
									className: 'table-cell-expand',
									label: 'Name',
									title: true
								},
								{
									accessor: 'properties.jobTitle',
									label: 'Job Title'
								},
								{
									accessor: 'properties.salary',
									label: 'Salary',
									sortable: false
								},
								{
									accessor: 'properties.country',
									label: 'Country'
								},
								{
									accessor: 'properties.age',
									label: 'Age'
								}
							],
							defaultSort: {
								field: 'name',
								sortOrder: 'desc'
							},
							rowIdentifier: 'id'
						}
					]}
					rowIdentifier='value'
				/>

				<h3>{'Table with Quick Actions'}</h3>

				<Table
					columns={[
						{
							accessor: 'name',
							cellRenderer: NameCell,
							className: 'table-cell-expand',
							label: 'NameCell',
							title: true
						},
						{
							accessor: 'properties.jobTitle',
							label: 'Job Title'
						},
						{
							accessor: 'properties.salary',
							label: 'Salary',
							sortable: false
						}
					]}
					defaultSort={{
						field: 'name'
					}}
					items={INDIVIDUALS}
					renderRowActions={RowActionsCell}
					rowIdentifier='id'
				/>

				<h3>{'Cell components'}</h3>
				<h5>{'Reusable Custom Cell Renderers'}</h5>

				<Table
					columns={[
						{
							accessor: 'name',
							cellRenderer: NameCell,
							label: 'NameCell',
							title: true
						},
						{
							accessor: 'name',
							cellRenderer: NameCell,
							cellRendererProps: {
								route: Routes.CONTACTS_INDIVIDUAL
							},
							label: 'NameCellLink',
							title: true
						},
						{
							accessor: 'name',
							cellRenderer: NameCell,
							cellRendererProps: {
								renderSecondaryInfo: data =>
									data.properties.jobTitle
							},
							label: 'NameCellWithSecondaryInfo',
							title: true
						},
						{
							accessor: 'name',
							cellRenderer: NameCell,
							cellRendererProps: {
								renderIcon: NameCellRenderIcon
							},
							label: 'NameCellWithSticker',
							title: true
						},
						{
							accessor: 'dateCreated',
							cellRenderer: DateCell,
							label: 'DateCell'
						}
					]}
					defaultSort={{
						field: 'name'
					}}
					items={INDIVIDUALS}
					rowIdentifier='id'
				/>
			</div>
		);
	}
}

export default TableKit;
