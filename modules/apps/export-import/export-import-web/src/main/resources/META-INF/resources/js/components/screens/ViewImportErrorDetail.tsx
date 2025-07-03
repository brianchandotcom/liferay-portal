import React, { useEffect, useState } from 'react';
import {fetch} from 'frontend-js-web';
import {
	openModal
} from 'frontend-js-components-web';
import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import formatDate from '../../utils/formatDate';

interface ErrorDetail {
    creator: {
        name: string,
    },
    dateCreated: string,
    dateModified: string,
    entityExternalReferenceCode: string,
    entityId: number,
    entityScope: string,
    entitySite: string,
    entityType: string,
    errorId: number,
    errorMessage: string,
    errorStackTrace: string,
    errorType: string,
    externalReferenceCode: string,
};

export function ViewImportErrorDetail()
 {

    const [errorDetail, setErrorDetail] = useState<ErrorDetail>({
        creator: {
            name: '',
        },
        dateCreated: '',
        dateModified: '',
        entityExternalReferenceCode: '',
        entityId: 0,
        entityScope: '',
        entitySite: '',
        entityType: '',
        errorId: 0,
        errorMessage: '',
        errorStackTrace: '',
        errorType: '',
        externalReferenceCode: '',
    });
    const [formatedDate, setFormattedDate] = useState<string>('');

    useEffect(() => {
        fetch('/group/__mocks__/get-import-error-detail').then((response) => {
            response.json().then((data: ErrorDetail) => {
                setErrorDetail(data);
            });
        });
    }, []);

    useEffect(() => {
        if (errorDetail.dateCreated) {
            const formattedDate = formatDate(errorDetail.dateCreated);
            setFormattedDate(formattedDate);
        }
    }, [errorDetail.dateCreated]);

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
                }
            ],
            size: 'full-screen',
            title: Liferay.Language.get('stack-trace'),
        });
    }

    const {
        creator,
        errorId,
        entityExternalReferenceCode,
        entityId,
        entityScope,
        entitySite,
        entityType,
        errorMessage,
        errorStackTrace,
        errorType
     } = errorDetail;

	return (
		<ClayLayout.ContainerFluid>
			<ClayLayout.Sheet className="m-4">
				<ClayLayout.SheetHeader>
					<h2 className="sheet-title">{entityType}</h2>

					<div className="sheet-text">
						{`${formatedDate} · ${creator.name}`}
					</div>
				</ClayLayout.SheetHeader>

				<ClayLayout.SheetSection>
					<span className="sheet-subtitle text-secondary">
						{Liferay.Language.get('error-details')}
					</span>

					<ClayLayout.ContentRow>
						<ClayLayout.Col className="pl-0" md={4}>
							<div className="sheet-text">
								<ClayLayout.ContentCol className="text-body">
									<strong>
										{Liferay.Language.get('error-id')}
									</strong>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol>
									{errorId}
								</ClayLayout.ContentCol>
							</div>
						</ClayLayout.Col>

						<ClayLayout.Col md={4}>
							<div className="sheet-text">
								<ClayLayout.ContentCol className="text-body">
									<strong>
										{Liferay.Language.get('error-type')}
									</strong>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol>
									{errorType}
								</ClayLayout.ContentCol>
							</div>
						</ClayLayout.Col>

						<ClayLayout.Col md={4}>
							<div className="sheet-text">
								<ClayLayout.ContentCol className="text-body">
									<strong>
										{Liferay.Language.get('entity-type')}
									</strong>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol>
									{entityType}
								</ClayLayout.ContentCol>
							</div>
						</ClayLayout.Col>
					</ClayLayout.ContentRow>

					<ClayLayout.ContentRow>
						<ClayLayout.Col className="pl-0" md={12}>
							<div className="sheet-text">
								<ClayLayout.ContentCol className="text-body">
									<strong>
										{Liferay.Language.get('error-message')}
									</strong>
								</ClayLayout.ContentCol>

								<ClayLayout.ContentCol className="text-justify">
									<textarea
										className="form-control lfr-textarea"
										readOnly
										rows={5}
										value={errorMessage}
									/>
								</ClayLayout.ContentCol>
							</div>
						</ClayLayout.Col>
					</ClayLayout.ContentRow>

					<div className="sheet-text">
						<ClayButton
							displayType="secondary"
							onClick={() =>
								openStackTraceModal({
									stackTraceMessage: errorStackTrace,
								})
							}
						>
							{Liferay.Language.get('view-stack-trace')}
						</ClayButton>
					</div>
				</ClayLayout.SheetSection>

				<ClayLayout.SheetSection>
					<span className="sheet-subtitle text-secondary">
						{Liferay.Language.get('failed-event')}
					</span>

					<div className="sheet-text">
						<ClayLayout.Row>
							<ClayLayout.Col md={6}>
								<div className="sheet-text">
									<ClayLayout.ContentCol className="text-body">
										<strong>
											{Liferay.Language.get('entity-id')}
										</strong>
									</ClayLayout.ContentCol>

									<ClayLayout.ContentCol>
										{entityId}
									</ClayLayout.ContentCol>
								</div>
							</ClayLayout.Col>

							<ClayLayout.Col md={6}>
								<div className="sheet-text">
									<ClayLayout.ContentCol className="text-body">
										<strong>
											{Liferay.Language.get('erc')}
										</strong>
									</ClayLayout.ContentCol>

									<ClayLayout.ContentCol>
										{entityExternalReferenceCode}
									</ClayLayout.ContentCol>
								</div>
							</ClayLayout.Col>
						</ClayLayout.Row>

						<ClayLayout.Row>
							<ClayLayout.Col md={6}>
								<div className="sheet-text">
									<ClayLayout.ContentCol className="text-body">
										<strong>
											{Liferay.Language.get('scope')}
										</strong>
									</ClayLayout.ContentCol>

									<ClayLayout.ContentCol>
										{entityScope}
									</ClayLayout.ContentCol>
								</div>
							</ClayLayout.Col>

							<ClayLayout.Col md={6}>
								<div className="sheet-text">
									<ClayLayout.ContentCol className="text-body">
										<strong>
											{Liferay.Language.get('site')}
										</strong>
									</ClayLayout.ContentCol>

									<ClayLayout.ContentCol>
										{entitySite}
									</ClayLayout.ContentCol>
								</div>
							</ClayLayout.Col>
						</ClayLayout.Row>
					</div>
				</ClayLayout.SheetSection>
			</ClayLayout.Sheet>

			<ClayButton
				className="ml-4"
				displayType="secondary"
				onClick={() => window.history.back()}
			>
				{Liferay.Language.get('back')}
			</ClayButton>
		</ClayLayout.ContainerFluid>
	);
}
