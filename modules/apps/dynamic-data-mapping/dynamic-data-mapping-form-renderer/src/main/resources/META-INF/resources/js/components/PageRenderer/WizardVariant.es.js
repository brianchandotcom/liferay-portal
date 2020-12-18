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

import ClayButton from '@clayui/button';
import classnames from 'classnames';
import React from 'react';

import {MultiStep} from '../MultiStep.es';
import {PaginationControls} from '../PaginationControls.es';

/* eslint-disable react/jsx-fragments */
export const Container = ({
	activePage,
	children,
	editable,
	pageIndex,
	pages,
	readOnly,
	showSubmitButton,
	strings = null,
	submitLabel,
}) => (
	<div className="ddm-form-page-container wizard">
		{pages.length > 1 && pageIndex === activePage && (
			<MultiStep
				activePage={activePage}
				editable={editable}
				pages={pages}
			/>
		)}

		<div
			className={classnames(
				'ddm-layout-builder ddm-page-container-layout',
				{
					hide: activePage !== pageIndex,
				}
			)}
		>
			<div className="form-builder-layout">{children}</div>
		</div>

		{pageIndex === activePage && (
			<React.Fragment>
				{pages.length > 0 && (
					<PaginationControls
						activePage={activePage}
						readOnly={readOnly}
						showSubmitButton={showSubmitButton}
						strings={strings}
						submitLabel={submitLabel}
						total={pages.length}
					/>
				)}

				{!pages.length && showSubmitButton && (
					<ClayButton
						className="float-right lfr-ddm-form-submit"
						id="ddm-form-submit"
						type="submit"
					>
						{submitLabel}
					</ClayButton>
				)}
			</React.Fragment>
		)}
	</div>
);

Container.displayName = 'WizardVariant.Container';
