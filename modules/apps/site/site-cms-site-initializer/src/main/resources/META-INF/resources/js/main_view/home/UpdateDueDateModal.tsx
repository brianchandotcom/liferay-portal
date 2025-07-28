/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import DatePicker from '@clayui/date-picker';
import {ClayInput} from '@clayui/form';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import TimePicker from '@clayui/time-picker';
import React, {useEffect, useState} from 'react';

import {updateDueDate} from '../../common/services/WorkflowService';

interface UpdateDueDateProps {
	dateDue: string;
	fetchWorkflowTasks: () => Promise<void>,
	modalProps: {
		observer: Observer;
		onOpenChange: (value: boolean) => void;
		open: boolean;
	};
	workflowTaskId: number;
}

export default function UpdateDueDateModal({
	dateDue,
	fetchWorkflowTasks,
	modalProps,
	workflowTaskId,
}: UpdateDueDateProps) {
	const {observer, onOpenChange, open} = modalProps;
	const [date, setDate] = useState('');
	const [time, setTime] = useState({
		ampm: '--',
		hours: '--',
		minutes: '--',
	});
	const [comment, setComment] = useState('');

	useEffect(() => {
		const date = dateDue ? new Date(dateDue) : new Date();

		const localDateString = `${date.getUTCFullYear()}-${String(date.getUTCMonth() + 1).padStart(2, '0')}-${String(date.getUTCDate()).padStart(2, '0')}`;
		let utcHours = date.getUTCHours();
		const ampm = utcHours >= 12 ? 'PM' : 'AM';

		utcHours = utcHours % 12;

		utcHours = utcHours ? utcHours : 12;

		setDate(localDateString);
		setTime({
			ampm,
			hours: String(utcHours).padStart(2, '0'),
			minutes: String(date.getUTCMinutes()).padStart(2, '0'),
		});

		return () => {
			setDate('');
			setTime({
				ampm: '--',
				hours: '--',
				minutes: '--',
			});
			setComment('');
		};
	}, [dateDue, open]);

	return (
		<>
			{open && (
				<ClayModal center observer={observer} size="lg">
					<ClayModal.Header>
						{Liferay.Language.get('update-due-date')}
					</ClayModal.Header>

					<ClayModal.Body>
						<div>
							<label>{Liferay.Language.get('due-date')}</label>

							<div className="row">
								<div className="col-6">
									<DatePicker
										onChange={setDate}
										value={date}
										years={{
											end: new Date().getFullYear(),
											start: 1910,
										}}
									/>
								</div>

								<div className="col-6">
									<TimePicker
										onChange={setTime}
										use12Hours
										value={time}
									/>
								</div>
							</div>

							<label htmlFor="commentInput">
								{Liferay.Language.get('comment')}
							</label>

							<ClayInput
								component="textarea"
								id="commentInput"
								onChange={(event) =>
									setComment(event?.target.value)
								}
								placeholder={Liferay.Language.get('comment')}
								type="text"
								value={comment}
							/>
						</div>
					</ClayModal.Body>

					<ClayModal.Footer
						last={
							<Button.Group spaced>
								<Button
									displayType="secondary"
									onClick={() => onOpenChange(false)}
								>
									{Liferay.Language.get('cancel')}
								</Button>

								<Button
									onClick={async () => {
										if (date && time) {
											const dateObj = new Date(date);

											const dateString = `${dateObj.getUTCFullYear()}-${String(dateObj.getUTCMonth() + 1).padStart(2, '0')}-${String(dateObj.getUTCDate()).padStart(2, '0')}`;

											let parsedHours = parseInt(
												time.hours,
												10
											);

											if (
												time.ampm.toUpperCase() ===
													'PM' &&
												parsedHours !== 12
											) {
												parsedHours += 12;
											}
											if (
												time.ampm.toUpperCase() ===
													'AM' &&
												parsedHours === 12
											) {
												parsedHours = 0;
											}

											const timeString = `${String(parsedHours).padStart(2, '0')}:${String(time.minutes).padStart(2, '0')}:00`;

											const completeDateStringInUTC = `${dateString}T${timeString}.000Z`;
											const updatedDueDate = new Date(
												completeDateStringInUTC
											);

											const res = await updateDueDate({
												comment,
												dueDate:
													updatedDueDate.toISOString(),
												workflowTaskId,
											});

											if (res.error) {
												Liferay.Util.openToast({
													message:
														'Due date failed to update.',
													type: 'error',
												});
											}
											else {
												Liferay.Util.openToast({
													message:
														'Due date updated sucessfully.',
													type: 'success',
												});
												await fetchWorkflowTasks();
												onOpenChange(false);
											}
										}

									}}
								>
									{Liferay.Language.get('done')}
								</Button>
							</Button.Group>
						}
					/>
				</ClayModal>
			)}
		</>
	);
}
