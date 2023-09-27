/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import * as DefaultVariant from '../../../core/components/PageRenderer/DefaultVariant.es';
import {useConfig} from '../../../core/hooks/useConfig.es';
import {MultiStep} from '../components/MultiStep.es';
import {PaginationControls} from '../components/PaginationControls.es';
import PartialResults from '../components/PartialResults';

export function Column({children, column, columnRef, editable, ...otherProps}) {
	const firstField = column.fields[0];

	return (
		<DefaultVariant.Column
			{...otherProps}
			column={column}
			columnClassName={classNames({
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
	const [alertDismissed, setAlertDismissed] = useState(false);

	const {
		dataEngineModule,
		displayChartAsTable,
		formReportDataURL,
		showPartialResultsToRespondents,
		showSubmitButton,
		submitLabel,
	} = useConfig();

	const onClick = () => {
		setShowReport(true);

		if (
			document.querySelector(
				'.lfr-ddm__show-partial-results-alert--hidden'
			)
		) {
			setAlertDismissed(true);
		}
	};

	useEffect(() => {
		const backButton = document.querySelector(
			'.lfr-ddm__default-page-header-back-button'
		);
		const alertElement = document.querySelector(
			'.lfr-ddm__show-partial-results-alert'
		);
		if (alertDismissed) {
			alertElement.classList.add(
				'lfr-ddm__show-partial-results-alert--hidden'
			);
		}
		if (showReport) {
			backButton?.classList.remove('hide');
			backButton.addEventListener('click', () => setShowReport(false));
			alertElement.classList.add(
				'lfr-ddm__show-partial-results-alert--hidden'
			);
		}

		return () => {
			if (showPartialResultsToRespondents) {
				backButton?.classList.add('hide');
				alertElement?.classList.remove(
					'lfr-ddm__show-partial-results-alert--hidden'
				);
			}
		};
	}, [alertDismissed, showPartialResultsToRespondents, showReport]);

	useEffect(() => {
		document.getElementById('main-content').scrollTop = 0;
	}, [activePage]);

	return (
		<>
			{showReport ? (
				<PartialResults
					dataEngineModule={dataEngineModule}
					displayChartAsTable={displayChartAsTable}
					onShow={() => setShowReport(false)}
					reportDataURL={formReportDataURL}
				/>
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
						className={classNames(
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
							{!!pages.length && (
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
									className="float-left"
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
