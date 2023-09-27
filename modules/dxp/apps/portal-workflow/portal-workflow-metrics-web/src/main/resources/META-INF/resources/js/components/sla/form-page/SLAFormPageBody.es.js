/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import React, {useCallback, useContext} from 'react';

import ContentView from '../../../shared/components/content-view/ContentView.es';
import FormGroupWithStatus from '../../../shared/components/form/FormGroupWithStatus.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import {parse} from '../../../shared/components/router/queryString.es';
import {useToaster} from '../../../shared/components/toaster/hooks/useToaster.es';
import {usePageTitle} from '../../../shared/hooks/usePageTitle.es';
import {AppContext} from '../../AppContext.es';
import {SLAContext} from '../SLAContainer.es';
import {
	ALERT_MESSAGE,
	DURATION,
	HOURS,
	NAME,
	PAUSE_NODE_KEYS,
	START_NODE_KEYS,
	STOP_NODE_KEYS,
} from './SLAFormConstants.es';
import {AlertChange, AlertMessage} from './SLAFormPageAlerts.es';
import {SLAFormContext} from './SLAFormPageProvider.es';
import DurationSection from './sections/DurationSection.es';
import TimeFrameSection from './sections/TimeFrameSection.es';
import {
	hasErrors,
	validateDuration,
	validateHours,
	validateName,
	validateNodeKeys,
} from './util/slaFormUtil.es';

function Body({history, id, processId, query}) {
	const {defaultDelta} = useContext(AppContext);
	const {setSLAUpdated} = useContext(SLAContext);
	const {
		changeValue,
		errors,
		fetchNodes,
		resetNodes,
		saveSLA,
		setErrors,
		sla,
	} = useContext(SLAFormContext);
	const toaster = useToaster();

	const {slaInfoLink} = parse(query);

	usePageTitle(id ? sla.name : Liferay.Language.get('new-sla'));

	const handleErrors = (dataError) => {
		if (Array.isArray(dataError)) {
			dataError.forEach(({fieldName, message}) => {
				errors[fieldName || ALERT_MESSAGE] = message;
			});

			const nodeKeys = [PAUSE_NODE_KEYS, START_NODE_KEYS, STOP_NODE_KEYS];
			const nodeErrors = dataError.filter(({fieldName}) =>
				nodeKeys.includes(fieldName)
			);

			if (nodeErrors.length) {
				resetNodes();
				fetchNodes(processId);
			}

			setErrors({...errors});
		}
		else {
			toaster.danger(Liferay.Language.get('your-request-has-failed'));
		}
	};

	const handleSubmit = useCallback(() => {
		const newErrors = {
			...errors,
			[ALERT_MESSAGE]: '',
			[DURATION]: validateDuration(sla.days, sla.hours),
			[HOURS]: validateHours(sla.hours),
			[NAME]: validateName(sla.name),
			[PAUSE_NODE_KEYS]: '',
			[START_NODE_KEYS]: validateNodeKeys(sla.startNodeKeys.nodeKeys),
			[STOP_NODE_KEYS]: validateNodeKeys(sla.stopNodeKeys.nodeKeys),
		};

		if (
			(!sla.hours || sla.hours === '00:00') &&
			sla.days &&
			Number(sla.days) > 0
		) {
			newErrors[HOURS] = '';
		}

		if (hasErrors(newErrors)) {
			setErrors({
				...newErrors,
				[ALERT_MESSAGE]: Liferay.Language.get(
					'please-fill-in-the-required-fields'
				),
			});
		}
		else {
			saveSLA()
				.then(() => {
					if (id) {
						setSLAUpdated(true);

						history.goBack();

						toaster.success(
							Liferay.Language.get('sla-was-updated')
						);
					}
					else {
						if (slaInfoLink) {
							history.push({
								pathname: `/sla/${processId}/list/${defaultDelta}/1`,
								search: query,
							});
						}
						else {
							history.goBack();
						}

						toaster.success(Liferay.Language.get('sla-was-saved'));
					}
				})
				.catch(handleErrors);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [id, processId, saveSLA, sla]);

	const onChangeHandler = (validateFunction) => ({
		target: {name, value = ''},
	}) => {
		changeValue(name, value);

		if (typeof validateFunction === 'function') {
			validateFunction(value);
		}
	};

	const onNameChanged = (newName) => {
		setErrors({
			...errors,
			[ALERT_MESSAGE]: '',
			[NAME]: validateName(newName),
		});
	};

	const statesProps = {
		errorProps: {
			actionButton: <ReloadButton />,
			className: 'pb-5 pt-6 sheet sheet-lg',
			hideAnimation: true,
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
		},
		loadingProps: {className: 'py-5 sheet sheet-lg'},
	};

	return (
		<ContentView {...statesProps}>
			<div className="alert-container">
				{errors[ALERT_MESSAGE] && (
					<Body.AlertMessage message={errors[ALERT_MESSAGE]} />
				)}

				{sla.status === 2 && <Body.AlertChange />}
			</div>

			<ClayForm>
				<ClayLayout.Sheet size="lg">
					<ClayLayout.SheetHeader className="mb-0">
						<h2 className="sheet-title">
							{Liferay.Language.get('sla-definition')}
						</h2>
					</ClayLayout.SheetHeader>

					<ClayLayout.SheetSection className="mb-0">
						<ClayLayout.Row>
							<ClayLayout.Col sm="5">
								<FormGroupWithStatus
									className="form-group"
									error={errors[NAME]}
									htmlFor="slaName"
									label={Liferay.Language.get('name')}
									requiredLabel
								>
									<ClayInput
										autoFocus
										className="form-control"
										id="slaName"
										maxLength={75}
										name="name"
										onChange={onChangeHandler(
											onNameChanged
										)}
										type="text"
										value={sla.name}
									/>
								</FormGroupWithStatus>
							</ClayLayout.Col>

							<ClayLayout.Col sm="7">
								<FormGroupWithStatus
									className="form-group"
									htmlFor="slaDescription"
									label={Liferay.Language.get('description')}
								>
									<ClayInput
										id="slaDescription"
										name="description"
										onChange={onChangeHandler()}
										type="text"
										value={sla.description}
									/>
								</FormGroupWithStatus>
							</ClayLayout.Col>
						</ClayLayout.Row>

						<Body.TimeFrameSection />

						<Body.DurationSection
							onChangeHandler={onChangeHandler}
						/>
					</ClayLayout.SheetSection>

					<ClayLayout.SheetFooter className="sheet-footer-btn-block-sm-down">
						<ClayButton.Group spaced>
							<ClayButton onClick={handleSubmit}>
								{id
									? Liferay.Language.get('update')
									: Liferay.Language.get('save')}
							</ClayButton>

							<ClayButton
								displayType="secondary"
								onClick={() => history.goBack()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>
						</ClayButton.Group>
					</ClayLayout.SheetFooter>
				</ClayLayout.Sheet>
			</ClayForm>
		</ContentView>
	);
}

Body.AlertChange = AlertChange;
Body.AlertMessage = AlertMessage;
Body.DurationSection = DurationSection;
Body.TimeFrameSection = TimeFrameSection;

export default Body;
