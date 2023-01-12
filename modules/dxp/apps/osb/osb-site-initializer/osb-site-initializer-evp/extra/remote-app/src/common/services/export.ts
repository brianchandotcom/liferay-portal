/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import fetcher from './fetcher';

const resource = 'o/headless-batch-engine/v1.0';
const objectClassName = 'com.liferay.object.rest.dto.v1_0.ObjectEntry';

export async function exportTask(fileType: string) {
	const response = await fetcher.post(
		`${resource}/export-task/${objectClassName}/${fileType}?taskItemDelegateName=C_EVPRequest&fieldNames=r_organization_c_evpOrganizationId,fullName,dateCreated,requestDescription,requestType,grantRequestType,grantAmount,totalHoursRequested,startDate,endDate`
	);

	return response;
}

export async function readyToDownload(id: number) {
	const response = await fetcher(`${resource}/export-task/${id}`);

	return response;
}

export async function downloadContentById(id: number) {
	const fileName = 'request-report.zip';

	fetcher
		.downloadZipContent(`${resource}/export-task/${id}/content`)
		.then((response) => response)
		.then((blob: any) => {
			const link = document.createElement('a');
			link.href = URL.createObjectURL(blob);
			link.download = fileName;
			link.click();
		})
		.catch(console.error);
}
