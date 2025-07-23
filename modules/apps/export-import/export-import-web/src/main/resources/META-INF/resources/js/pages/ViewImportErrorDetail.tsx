/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {openModal} from 'frontend-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import formatDate from '../utils/formatDate';

function DetailViewDefinitionCol({
	body,
	title,
	...colProps
}: {
	body: React.ReactNode;
	title?: string;
} & React.ComponentProps<typeof ClayLayout.Col>): JSX.Element {
	return (
		<ClayLayout.Col {...colProps}>
			{title && (
				<div>
					<strong>{title}</strong>
				</div>
			)}

			<div className="sheet-text">{body}</div>
		</ClayLayout.Col>
	);
}

interface ErrorDetail {
	creator: {
		name: string;
	};
	dateCreated: string;
	dateModified: string;
	entityExternalReferenceCode: string;
	entityId: number;
	entityScope: string;
	entitySite: string;
	entityType: string;
	errorId: number;
	errorMessage: string;
	errorStackTrace: string;
	errorType: string;
	externalReferenceCode: string;
}

export function ViewImportErrorDetail({
	apiURL,
	backURL,
}: {
	apiURL: string;
	backURL: string;
}) {
	const [isLoading, setIsLoading] = useState(true);
	const [errorDetail, setErrorDetail] = useState<ErrorDetail>(
		{} as ErrorDetail
	);

	useEffect(() => {
		fetch(apiURL).then((response) => {
			response.json().then((data: ErrorDetail) => {
				setErrorDetail({
					...data,
					...(data.dateCreated && {
						dateCreated: formatDate(data.dateCreated),
					}),
				});
				setIsLoading(false);
			});
		});
	}, [apiURL]);

	function openStackTraceModal({
		stackTraceMessage,
	}: {
		stackTraceMessage: string;
	}) {
		openModal({
			bodyHTML: `
				<div class="bg-dark border border-light p-4 rounded">
					<p class="text-white">
                        ${stackTraceMessage}
					</p>
				</div>
			`,
			buttons: [
				{
					displayType: 'secondary',
					label: Liferay.Language.get('close'),
					onClick: ({processClose}: {processClose: Function}) => {
						processClose();
					},
				},
			],
			size: 'full-screen',
			title: Liferay.Language.get('stack-trace'),
		});
	}

	const {
		creator,
		dateCreated,
		entityExternalReferenceCode,
		entityId,
		entityScope,
		entitySite,
		entityType,
		errorId,
		errorMessage,
		errorStackTrace,
		errorType,
	} = errorDetail;

	return (
		<ClayLayout.ContainerFluid>
			{isLoading ? (
				<div className="align-items-center d-flex justify-content-center mt-4">
					<ClayLoadingIndicator title="Loading import error details..." />
				</div>
			) : (
				<>
					<ClayLayout.Sheet className="mt-4">
						<ClayLayout.SheetHeader>
							<h2 className="sheet-title">{entityType}</h2>

							<div className="sheet-text">
								{`${dateCreated} · ${creator.name}`}
							</div>
						</ClayLayout.SheetHeader>

						<ClayLayout.SheetSection className="mb-2">
							<span className="sheet-subtitle text-secondary">
								{Liferay.Language.get('error-details')}
							</span>

							<ClayLayout.Row>
								<DetailViewDefinitionCol
									body={errorId.toString()}
									md={4}
									title={Liferay.Language.get('error-id')}
								/>

								<DetailViewDefinitionCol
									body={errorType}
									md={4}
									title={Liferay.Language.get('error-type')}
								/>

								<DetailViewDefinitionCol
									body={entityType}
									md={4}
									title={Liferay.Language.get('entity-type')}
								/>
							</ClayLayout.Row>

							<ClayLayout.Row>
								<DetailViewDefinitionCol
									body={
										<textarea
											className="form-control lfr-textarea"
											readOnly
											rows={5}
											value={errorMessage}
										/>
									}
									title={Liferay.Language.get(
										'error-message'
									)}
								/>
							</ClayLayout.Row>

							<ClayLayout.Row>
								<DetailViewDefinitionCol
									body={
										<ClayButton
											displayType="secondary"
											onClick={() =>
												openStackTraceModal({
													stackTraceMessage:
														errorStackTrace,
												})
											}
										>
											{Liferay.Language.get(
												'view-stack-trace'
											)}
										</ClayButton>
									}
								/>
							</ClayLayout.Row>
						</ClayLayout.SheetSection>

						<ClayLayout.SheetSection>
							<span className="sheet-subtitle text-secondary">
								{Liferay.Language.get('failed-event')}
							</span>

							<ClayLayout.Row>
								<DetailViewDefinitionCol
									body={entityId.toString()}
									md={6}
									title={Liferay.Language.get('entity-id')}
								/>

								<DetailViewDefinitionCol
									body={entityExternalReferenceCode}
									md={6}
									title={Liferay.Language.get(
										'external-reference-code'
									)}
								/>
							</ClayLayout.Row>

							<ClayLayout.Row>
								<DetailViewDefinitionCol
									body={entityScope}
									md={6}
									title={Liferay.Language.get('scope')}
								/>

								<DetailViewDefinitionCol
									body={entitySite}
									md={6}
									title={Liferay.Language.get('site')}
								/>
							</ClayLayout.Row>
						</ClayLayout.SheetSection>
					</ClayLayout.Sheet>
				</>
			)}

			<ClayLayout.SheetFooter>
				<ClayLink button displayType="secondary" href={backURL}>
					{Liferay.Language.get('back')}
				</ClayLink>
			</ClayLayout.SheetFooter>
		</ClayLayout.ContainerFluid>
	);
}
