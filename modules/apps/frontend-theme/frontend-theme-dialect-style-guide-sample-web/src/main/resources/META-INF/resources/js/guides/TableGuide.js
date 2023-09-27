/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTable from '@clayui/table';
import React from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const TABLE_VARIANTS = [
	{
		label: 'table',
	},
	{
		className: 'table-list',
		label: 'table-divided',
	},
	{
		className: 'table-spaced',
		tokenClassName: 'bg-neutral-3 px-2',
	},
	{
		className: 'table-bordered',
	},
];

const TABLE_DENSITIES = [
	{
		label: 'table-md',
	},
	{
		className: 'table-sm',
	},
	{
		className: 'table-lg',
	},
];

const TABLE_UTILITY_CLASSES = [
	{
		className: 'table-striped',
	},
	{
		className: 'table-hover',
		hover: true,
	},
	{
		className: 'table-responsive',
		responsive: true,
	},
	{
		className: 'table-valign-bottom',
	},
	{
		className: 'table-valign-middle',
	},
	{
		className: 'table-valign-top',
	},
];

const BIG_TEXT =
	'Lorem ipsum dolor sit amet, con Lorem ipsum dolor sit amet, con Lorem ipsum dolor Lorem ipsum dolor sit amet, con Lorem ips Lorem';

const Table = ({className, hover = false, responsive = false}) => {
	return (
		<ClayTable
			borderedColumns={false}
			borderless={true}
			className={className}
			hover={hover}
			responsive={responsive}
		>
			<ClayTable.Head>
				<ClayTable.Row>
					<ClayTable.Cell headingCell>Teams</ClayTable.Cell>

					<ClayTable.Cell headingCell>Region</ClayTable.Cell>

					<ClayTable.Cell headingCell>Country</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Head>

			<ClayTable.Body>
				<ClayTable.Row>
					<ClayTable.Cell headingTitle>White and Red</ClayTable.Cell>

					<ClayTable.Cell>South America</ClayTable.Cell>

					<ClayTable.Cell>Brazil</ClayTable.Cell>
				</ClayTable.Row>

				<ClayTable.Row>
					<ClayTable.Cell headingTitle>
						White and Purple
					</ClayTable.Cell>

					<ClayTable.Cell>Europe</ClayTable.Cell>

					<ClayTable.Cell>Spain</ClayTable.Cell>
				</ClayTable.Row>
			</ClayTable.Body>
		</ClayTable>
	);
};

const TableTokenItem = ({item}) => (
	<TokenItem
		className={item.tokenClassName}
		label={item.label ? item.label : item.className}
		size="large"
	>
		<Table
			className={item.className}
			hover={item.hover}
			responsive={item.responsive}
		/>
	</TokenItem>
);

function getKeyFromObject(object) {
	return Object.values(object).join('-');
}

const TableGuide = () => {
	return (
		<>
			<TokenGroup
				className="px-2"
				group="tables"
				title={Liferay.Language.get('tables')}
			>
				{TABLE_VARIANTS.map((item) => (
					<TableTokenItem item={item} key={getKeyFromObject(item)} />
				))}
			</TokenGroup>

			<TokenGroup group="density" title={Liferay.Language.get('density')}>
				{TABLE_DENSITIES.map((item) => (
					<TableTokenItem item={item} key={getKeyFromObject(item)} />
				))}
			</TokenGroup>

			<TokenGroup
				group="utility-classes"
				title={Liferay.Language.get('table-utility-classes')}
			>
				{TABLE_UTILITY_CLASSES.map((item) => (
					<TableTokenItem item={item} key={getKeyFromObject(item)} />
				))}
			</TokenGroup>

			<TokenGroup
				group="utility-classes"
				title={Liferay.Language.get('cell-utility-classes')}
			>
				<ClayTable
					borderedColumns={false}
					borderless={true}
					className="table-list"
					hover={true}
				>
					<ClayTable.Head>
						<ClayTable.Row>
							<ClayTable.Cell headingCell>Teams</ClayTable.Cell>

							<ClayTable.Cell headingCell>Region</ClayTable.Cell>

							<ClayTable.Cell headingCell>Country</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Head>

					<ClayTable.Body>
						<ClayTable.Row>
							<ClayTable.Cell headingTitle>
								White and Red
							</ClayTable.Cell>

							<ClayTable.Cell>
								<div className="table-list-title">
									.table-list-title (not a link)
								</div>

								<div className="table-list-title">
									<a href="#1">.table-list-title a</a>
								</div>

								<div>
									<a className="table-list-link" href="#1">
										.table-list-link
									</a>
								</div>

								<div>
									<a href="#1">link</a>
								</div>

								<div>Some regular text</div>
							</ClayTable.Cell>

							<ClayTable.Cell>
								<div className="table-title">
									.table-title (not a link)
								</div>

								<div className="table-title">
									<a href="#1">.table-title a</a>
								</div>

								<div>
									<a className="table-link" href="#1">
										.table-link
									</a>
								</div>

								<div>
									<a href="#1">link</a>
								</div>

								<div>Some regular text</div>
							</ClayTable.Cell>
						</ClayTable.Row>

						<ClayTable.Row className="table-column-text-start">
							<ClayTable.Cell headingTitle>
								.table-column-text-start
							</ClayTable.Cell>

							<ClayTable.Cell>Start</ClayTable.Cell>

							<ClayTable.Cell>Start</ClayTable.Cell>
						</ClayTable.Row>

						<ClayTable.Row className="table-column-text-center">
							<ClayTable.Cell headingTitle>
								.table-column-text-center
							</ClayTable.Cell>

							<ClayTable.Cell>Center</ClayTable.Cell>

							<ClayTable.Cell>Center</ClayTable.Cell>
						</ClayTable.Row>

						<ClayTable.Row className="table-column-text-end">
							<ClayTable.Cell headingTitle>
								.table-column-text-end
							</ClayTable.Cell>

							<ClayTable.Cell>End</ClayTable.Cell>

							<ClayTable.Cell>End</ClayTable.Cell>
						</ClayTable.Row>

						<ClayTable.Row>
							<ClayTable.Cell headingTitle>A row</ClayTable.Cell>

							<ClayTable.Cell>
								with the third column set to table-cell-expand
							</ClayTable.Cell>

							<ClayTable.Cell expanded>{BIG_TEXT}</ClayTable.Cell>
						</ClayTable.Row>

						<ClayTable.Row className="table-active">
							<ClayTable.Cell headingTitle>
								<div className="custom-checkbox custom-control">
									<label>
										<input
											checked="true"
											className="custom-control-input"
											type="checkbox"
										/>

										<span className="custom-control-label"></span>
									</label>
								</div>
							</ClayTable.Cell>

							<ClayTable.Cell>table-active</ClayTable.Cell>

							<ClayTable.Cell>Item selected</ClayTable.Cell>
						</ClayTable.Row>
					</ClayTable.Body>
				</ClayTable>

				<TokenItem label="table-img" size="large">
					<ClayTable>
						<ClayTable.Head>
							<ClayTable.Row>
								<ClayTable.Cell headingCell>
									Object
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									Size
								</ClayTable.Cell>

								<ClayTable.Cell headingCell>
									Image
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Head>

						<ClayTable.Body>
							<ClayTable.Row>
								<ClayTable.Cell headingTitle>#1</ClayTable.Cell>

								<ClayTable.Cell>60x30</ClayTable.Cell>

								<ClayTable.Cell expanded>
									<img
										className="table-img"
										src="https://via.placeholder.com/60x30"
									/>
								</ClayTable.Cell>
							</ClayTable.Row>

							<ClayTable.Row>
								<ClayTable.Cell headingTitle>#2</ClayTable.Cell>

								<ClayTable.Cell>60x120</ClayTable.Cell>

								<ClayTable.Cell expanded>
									<img
										className="table-img"
										src="https://via.placeholder.com/60x120"
									/>
								</ClayTable.Cell>
							</ClayTable.Row>

							<ClayTable.Row>
								<ClayTable.Cell headingTitle>#3</ClayTable.Cell>

								<ClayTable.Cell>100x100</ClayTable.Cell>

								<ClayTable.Cell expanded>
									<img
										className="table-img"
										src="https://via.placeholder.com/100x100"
									/>
								</ClayTable.Cell>
							</ClayTable.Row>
						</ClayTable.Body>
					</ClayTable>
				</TokenItem>
			</TokenGroup>
		</>
	);
};

export default TableGuide;
