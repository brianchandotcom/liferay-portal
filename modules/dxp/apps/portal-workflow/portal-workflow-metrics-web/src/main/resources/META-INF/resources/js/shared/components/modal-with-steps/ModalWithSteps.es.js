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

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import ClayModal from '@clayui/modal';
import React from 'react';

import StepBar from '../step-bar/StepBar.es';

const ModalWithSteps = ({
	error,
	modalSize = 'lg',
	observer,
	step,
	stepsCount = 2,
	visible,
}) => {
	return (
		<>
			{visible && (
				<ClayModal observer={observer} size={modalSize}>
					<ClayModal.Header>{step.title}</ClayModal.Header>

					{error && (
						<ClayAlert
							className="mb-0"
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{error}
						</ClayAlert>
					)}

					<StepBar
						current={step.order}
						title={step.subtitle}
						total={stepsCount}
					/>

					<step.component {...step.props} />

					<ClayModal.Footer
						first={
							step.cancelBtn && (
								<ClayButton
									disabled={step.cancelBtn.disabled}
									displayType="secondary"
									onClick={step.cancelBtn.handle}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							)
						}
						last={
							<>
								{step.previousBtn && (
									<ClayButton
										className="mr-3"
										disabled={step.previousBtn.disabled}
										displayType="secondary"
										onClick={step.previousBtn.handle}
									>
										{Liferay.Language.get('previous')}
									</ClayButton>
								)}

								<ClayButton
									disabled={step.nextBtn.disabled}
									onClick={step.nextBtn.handle}
								>
									{step.nextBtn.text}
								</ClayButton>
							</>
						}
					/>
				</ClayModal>
			)}
		</>
	);
};

export default ModalWithSteps;
