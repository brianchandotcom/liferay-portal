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
import {useResource} from '@clayui/data-provider';
import ClayIcon from '@clayui/icon';
import classnames from 'classnames';
import {fetch} from 'frontend-js-web';
import React, {useState} from 'react';

import * as DefaultVariant from '../../../core/components/PageRenderer/DefaultVariant.es';
import {useConfig} from '../../../core/hooks/useConfig.es';
import FormReport from '../../form-report/index';
import {MultiStep} from '../components/MultiStep.es';
import {PaginationControls} from '../components/PaginationControls.es';

export function Column({children, column, columnRef, editable, ...otherProps}) {
	const firstField = column.fields[0];

	return (
		<DefaultVariant.Column
			{...otherProps}
			column={column}
			columnClassName={classnames({
				hide: firstField?.hideField && !editable,
			})}
			ref={columnRef}
		>
			{children}
		</DefaultVariant.Column>
	);
}

Column.displayName = 'WizardVariant.Column';

export function Container({
	activePage,
	children,
	editable,
	pageIndex,
	pages,
	readOnly,
	strings = null,
}) {
	const [showReport, setShowReport] = useState(false);

	const {formReportDataURL, showSubmitButton, submitLabel} = useConfig();

	const {resource} = useResource({
		fetch,
		link: formReportDataURL,
	});

	const onClick = () => {
		setShowReport(true);
	};

	if (showReport) {
		const showPartialResultsToRespondentsElement = document.querySelector(
			'[id$="showPartialResultsToRespondents"]'
		);

		showPartialResultsToRespondentsElement.style.display = 'none';
	}

	return (
		<>
			{showReport ? (
				<>
					<div className="ddm-form-page-back">
						<ClayButton
							displayType="link"
							onClick={() => setShowReport(false)}
						>
							<ClayIcon symbol="order-arrow-left" />
							{Liferay.Language.get('back')}
						</ClayButton>
					</div>

					<div
						className="portlet-ddm-form-report"
						id="container-portlet-ddm-form-report"
					>
						<div className="portlet-ddm-form-report-header">
							<div className="container-fluid">
								<div className="align-items-center">
									<span className="portlet-ddm-form-report-header-title text-truncate">
										{resource?.totalItems === 1
											? Liferay.Util.sub(
													Liferay.Language.get(
														'x-entry'
													),
													[resource?.totalItems]
											  )
											: Liferay.Util.sub(
													Liferay.Language.get(
														'x-entries'
													),
													[resource?.totalItems]
											  )}
									</span>
								</div>

								<div className="align-items-center">
									<span className="portlet-ddm-form-report-header-subtitle text-truncate">
										{resource?.totalItems > 0
											? resource?.lastModifiedDate
											: Liferay.Language.get(
													'there-are-no-entries'
											  )}
									</span>
								</div>
							</div>
						</div>

						<FormReport
							data={resource?.data}
							fields={resource?.fields}
							formReportRecordsFieldValuesURL={
								resource?.formReportRecordsFieldValuesURL
							}
							portletNamespace={resource?.portletNamespace}
						/>
					</div>
				</>
			) : (
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
						<>
							{pages.length > 0 && (
								<PaginationControls
									activePage={activePage}
									onClick={onClick}
									readOnly={readOnly}
									showSubmitButton={showSubmitButton}
									strings={strings}
									submitLabel={submitLabel}
									total={pages.length}
								/>
							)}

							{!pages.length && showSubmitButton && (
								<ClayButton
									className="float-left lfr-ddm-form-submit"
									id="ddm-form-submit"
									type="submit"
								>
									{submitLabel}
								</ClayButton>
							)}
						</>
					)}
				</div>
			)}
		</>
	);
}

Container.displayName = 'WizardVariant.Container';
