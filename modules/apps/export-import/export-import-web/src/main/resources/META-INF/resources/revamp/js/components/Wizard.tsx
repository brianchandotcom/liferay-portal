/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiStepNav from '@clayui/multi-step-nav';
import {Form, Formik, FormikHelpers} from 'formik';
import React, {ReactElement, useState} from 'react';

import Footer from './Footer';

type FormValues = {
	[key: string]: any;
};

interface WizardStepProps {
	actionButton?: React.ReactElement;
	children: React.ReactNode;
	description: string;
	onSubmit?: (values: FormValues) => Promise<void>;
	title: string;
}

export function WizardStep({children}: WizardStepProps) {
	return <>{children}</>;
}

export function Wizard({
	backURL,
	children,
}: {
	backURL: string;
	children:
		| React.ReactElement<WizardStepProps>
		| React.ReactElement<WizardStepProps>[];
}) {
	const [stepNumber, setStepNumber] = useState(0);
	const [formState, setFormState] = useState({});

	const steps = React.Children.toArray(
		children
	) as ReactElement<WizardStepProps>[];

	const totalSteps = steps.length;

	const step = steps[stepNumber] as React.ReactElement<WizardStepProps>;
	const {actionButton, description, onSubmit, title} = step.props;

	const next = () => {
		setStepNumber((stepNumber) => Math.min(stepNumber + 1, totalSteps - 1));
	};

	const handleSubmit = async (
		values: FormValues,
		formikHelpers: FormikHelpers<FormValues>
	) => {
		await onSubmit?.(values);

		setFormState((prevState) => ({
			...prevState,
			...values,
		}));

		formikHelpers.setTouched({});

		next();
	};

	return (
		<Formik initialValues={formState} onSubmit={handleSubmit}>
			{(formik) => (
				<Form noValidate>
					<ClayMultiStepNav
						center
						className="c-mx-lg-9"
						indicatorLabel="top"
					>
						{steps.map((step, index) => {
							const {title: multiStepTitle} = step.props;

							return (
								<ClayMultiStepNav.Item
									active={index === stepNumber}
									key={index}
									state={
										stepNumber > index
											? 'complete'
											: undefined
									}
								>
									{index < totalSteps - 1 && (
										<ClayMultiStepNav.Divider />
									)}

									<ClayMultiStepNav.Indicator
										label={1 + index}
										subTitle={multiStepTitle}
									/>
								</ClayMultiStepNav.Item>
							);
						})}
					</ClayMultiStepNav>

					<header className="mb-1 sheet-header">
						<div className="mb-1 sheet-title">{title}</div>

						{description && (
							<p className="sheet-text text-secondary">
								{description}
							</p>
						)}
					</header>

					{step}

					<Footer
						actionButton={actionButton}
						backURL={backURL}
						onPrevious={
							stepNumber > 0
								? () => setStepNumber(stepNumber - 1)
								: undefined
						}
					/>
				</Form>
			)}
		</Formik>
	);
}
