/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import classNames from 'classnames';
import React from 'react';

import Collapse from '../../../common/components/Collapse';

const layouts = [
	{
		columns: ['12']
	},
	{
		columns: ['6', '6']
	},
	{
		columns: ['8', '4']
	},
	{
		columns: ['4', '8']
	},
	{
		columns: ['4', '4', '4']
	},
	{
		columns: ['3', '3', '3', '3']
	}
];

const LayoutCard = ({layoutColumns}) => {
	return (
		<div
			aria-label={layoutColumns.join('-')}
			className={classNames(
				'page-editor-sidebar-panel__section-builder__card',
				'card',
				'card-interactive',
				'card-interactive-secondary',
				'selector-button'
			)}
		>
			<div className="card-body">
				<div className="card-row">
					<div className="autofit-col autofit-col-expand autofit-row-center">
						<div
							className={classNames(
								'page-editor-sidebar-panel__section-builder__layouts__layout-preview',
								'container'
							)}
						>
							<div
								className={classNames(
									'page-editor-sidebar-panel__section-builder__layouts__layout-preview__row',
									'row'
								)}
								role="image"
							>
								{layoutColumns.map((column, index) => (
									<div
										className={classNames(
											'page-editor-sidebar-panel__section-builder__layouts__layout-preview__col',
											`col-${column}`
										)}
										key={`${index}-${column}`}
									></div>
								))}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default function Layouts() {
	return (
		<>
			<Collapse label={Liferay.Language.get('layouts')} open={false}>
				<div className="page-editor-sidebar-panel__section-builder__layouts">
					{layouts.map(layout => {
						return (
							<LayoutCard
								key={`layout-of-${layout.columns.join('-')}`}
								layoutColumns={layout.columns}
							/>
						);
					})}
				</div>
			</Collapse>
		</>
	);
}
