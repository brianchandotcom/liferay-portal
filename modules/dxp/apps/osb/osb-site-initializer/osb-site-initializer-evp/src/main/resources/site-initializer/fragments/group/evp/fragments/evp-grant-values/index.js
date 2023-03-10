/* eslint-disable @liferay/portal/no-global-fetch */
/* eslint-disable eqeqeq */
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

const grantLimit = [];

const getGrantLimit = async () => {
	await fetch(`/o/c/evpgrantlimits`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	})
		.then((response) => response.json())
		.then((data) => grantLimit.push(data));
};

getGrantLimit();

const fundsLimit = [];
const serviceHoursLimit = [];

const getFundsLimit = async () => {
	await getGrantLimit();

	fundsLimit.push(grantLimit[0].items[0].fundsLimit);
};

const getServiceHoursLimit = async () => {
	await getGrantLimit();

	serviceHoursLimit.push(grantLimit[0].items[0].serviceHoursLimit);
};

getFundsLimit();
getServiceHoursLimit();

const requests = [];
const userId = document.getElementById('userIdContainer').textContent;

const getRequests = async () => {
	await fetch(`/o/c/evprequests`, {
		headers: {
			'content-type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method: 'GET',
	})
		.then((response) => response.json())
		.then((data) => requests.push(data));
};

getRequests();

const fundsRequestsByUserId = [];
const totalFundsRequestedById = [];
const serviceHoursRequestsByUserId = [];
const totalHoursRequestedByUserId = [];

const getFundsRequestByUserId = async () => {
	await getRequests();

	requests[0].items.map((item) => {
		if (
			item.creator.id == userId &&
			item.requestStatus.key !== 'rejected'
		) {
			fundsRequestsByUserId.push(item.grantAmount);
		}

		return null;
	});

	totalFundsRequestedById.push(
		fundsRequestsByUserId.reduce((total, quantity) => total + quantity)
	);
};

const getServiceHoursByUserId = async () => {
	await getRequests();

	requests[0].items.map((item) => {
		if (
			item.creator.id == userId &&
			item.requestStatus.key !== 'rejected'
		) {
			serviceHoursRequestsByUserId.push(item.totalHoursRequested);
		}

		return null;
	});

	totalHoursRequestedByUserId.push(
		serviceHoursRequestsByUserId.reduce(
			(total, quantity) => total + quantity
		)
	);
};

getFundsRequestByUserId();
getServiceHoursByUserId();

const availableFunds = [];
const availableServiceHours = [];

const getAvailableFunds = async () => {
	await Promise.all([getFundsRequestByUserId(), getFundsLimit()]);

	availableFunds.push(fundsLimit[0] - totalFundsRequestedById[0]);
	document.querySelector('#available-funds').innerHTML =
		' R$ ' + availableFunds[0];
};

getAvailableFunds();

const getAvailableHours = async () => {
	await Promise.all([getServiceHoursByUserId(), getServiceHoursLimit()]);

	availableServiceHours.push(
		serviceHoursLimit[0] - totalHoursRequestedByUserId[0]
	);
	document.querySelector('#available-hours').innerHTML =
		availableServiceHours[0] + 'h';
};

getAvailableHours();
