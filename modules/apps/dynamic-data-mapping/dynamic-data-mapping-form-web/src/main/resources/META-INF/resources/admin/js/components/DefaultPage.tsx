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
import {PartialResults} from 'data-engine-js-components-web';
import React, {useEffect, useState} from 'react';

import './DefaultPage.scss';
import DefaultPageHeader from './DefaultPageHeader';

const DefaultPage: React.FC<IProps> = ({
	formDescription,
	formReportDataURL,
	formTitle,
	pageDescription,
	pageTitle,
	showPartialResultsToRespondents,
	showSubmitAgainButton,
}) => {
	const [showReport, setShowReport] = useState(false);

	useEffect(() => {
		const portalPopup = document.querySelector('.portal-popup');
		portalPopup?.classList.add('lfr-ddm__default-page-background');

		return () => {
			portalPopup?.classList.remove('lfr-ddm__default-page-background');
		};
	}, []);

	return (
		<>
			<div className="container-fluid container-fluid-max-xl lfr-ddm__default-page">
				<DefaultPageHeader
					description={formDescription}
					onClickBack={
						showReport ? () => setShowReport(false) : undefined
					}
					title={formTitle}
				/>

				{showReport ? (
					<PartialResults
						reportDataURL={formReportDataURL as string}
					/>
				) : (
					<div className="lfr-ddm__default-page-container">
						<h2 className="lfr-ddm__default-page-title">
							{pageTitle}
						</h2>

						<p className="lfr-ddm__default-page-description">
							{pageDescription}
						</p>

						<div className="lfr-ddm__default-page-buttons">
							{showSubmitAgainButton && (
								<ClayButton
									displayType="secondary"
									onClick={() => window.location.reload()}
								>
									{Liferay.Language.get('submit-again')}
								</ClayButton>
							)}

							{showPartialResultsToRespondents &&
								formReportDataURL && (
									<ClayButton
										displayType="secondary"
										onClick={() => setShowReport(true)}
									>
										{Liferay.Language.get(
											'see-partial-results'
										)}
									</ClayButton>
								)}
						</div>
					</div>
				)}
			</div>
		</>
	);
};

DefaultPage.displayName = 'DefaultPage';

export default DefaultPage;

interface IProps {
	formDescription?: string;
	formReportDataURL?: string;
	formTitle: string;
	pageDescription: string;
	pageTitle: string;
	showPartialResultsToRespondents?: boolean;
	showSubmitAgainButton?: boolean;
}
