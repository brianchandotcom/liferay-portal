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
import {useContext} from 'react';
import {SelectedQuoteContext} from '../../context/SelectedQuoteContextProvider';

const Panel = ({
	children,
	id,
	Middle = () => null,
	Right = () => null,
	title = '',
	titleNumber = '',
	hasError = false,
}) => {
	const [{panel}] = useContext(SelectedQuoteContext);
	const {checked, expanded = false} = panel[id];

	const show = expanded || hasError;

	return (
		<div className="p-0 panel-container px-md-6 py-5 w-100">
			<div className="d-flex flex-wrap justify-content-between panel-header px-0">
				<div className="d-flex font-weight-bolder h4 mb-0 order-first text-neutral-9">
					<div className="mr-1">{titleNumber}.</div>

					<span
						className={classNames('', {
							'break-title': id === 'uploadDocuments',
						})}
					>
						{title}
					</span>
				</div>

				<Middle checked={checked} expanded={expanded} />

				<Right checked={checked} expanded={expanded} />
			</div>

			<div
				className={classNames('panel-content', {
					'd-block': show,
					'd-none': !show,
				})}
			>
				{show && children}
			</div>
		</div>
	);
};

export default Panel;
