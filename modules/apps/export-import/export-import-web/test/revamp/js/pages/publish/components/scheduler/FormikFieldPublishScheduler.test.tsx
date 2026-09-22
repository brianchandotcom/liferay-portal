/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import {Formik} from 'formik';
import React from 'react';

import '@testing-library/jest-dom';

import {FormikFieldPublishScheduler} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/FormikFieldPublishScheduler';
import {ScheduleValues} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/types';
import {getInitialScheduleValues} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/utils';
import {toWallClockDateTime} from '../../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/publish/components/scheduler/cron';

const DAY = 24 * 60 * 60 * 1000;

const FUTURE_START_DATE_TIME = toWallClockDateTime(
	new Date(Date.now() + DAY).toISOString(),
	'UTC'
);

const PAST_START_DATE_TIME = toWallClockDateTime(
	new Date(Date.now() - DAY).toISOString(),
	'UTC'
);

function renderFormikFieldPublishScheduler(
	partialScheduleValues: Partial<ScheduleValues>
) {
	return render(
		<Formik
			initialValues={{
				scheduleValues: {
					...getInitialScheduleValues('UTC'),
					...partialScheduleValues,
				},
			}}
			onSubmit={jest.fn()}
		>
			<FormikFieldPublishScheduler
				name="scheduleValues"
				timeZones={[
					{label: '(UTC) Coordinated Universal Time', value: 'UTC'},
				]}
			/>
		</Formik>
	);
}

describe('FormikFieldPublishScheduler', () => {
	it('stays quiet while the start date is still blank', () => {
		renderFormikFieldPublishScheduler({enabled: true});

		expect(
			screen.queryByText(
				'please-set-a-start-date-and-time-to-schedule-the-publication'
			)
		).not.toBeInTheDocument();
	});

	it('shows the start date error for an untouched value that is already wrong', () => {
		renderFormikFieldPublishScheduler({
			enabled: true,
			startDateTime: PAST_START_DATE_TIME,
		});

		expect(
			screen.getByText('the-publish-time-must-be-in-the-future')
		).toBeInTheDocument();
	});

});
