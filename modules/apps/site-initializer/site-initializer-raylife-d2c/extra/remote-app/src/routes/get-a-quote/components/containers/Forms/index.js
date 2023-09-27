/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';
import {AppContext} from '../../../context/AppContextProvider';
import FormActionProvider, {
	FormActionContext,
} from '../../../context/FormActionProvider';
import FormCard from '../../card/FormCard';
import {Forms} from '../Layout/FormBody';
import {FormFooterDesktop, FormFooterMobile} from '../Layout/FormFooter';

const FormLayout = ({form}) => {
	const {
		state: {
			selectedStep: {index: currentStepIndex},
		},
	} = useContext(AppContext);

	return (
		<FormActionProvider form={form}>
			<FormActionContext.Consumer>
				{(formActionContext) => (
					<>
						<FormCard>
							<Forms
								currentStepIndex={currentStepIndex}
								form={form}
								formActionContext={formActionContext}
							/>

							<FormFooterDesktop
								formActionContext={formActionContext}
							/>
						</FormCard>

						<FormFooterMobile
							formActionContext={formActionContext}
						/>
					</>
				)}
			</FormActionContext.Consumer>
		</FormActionProvider>
	);
};

export {FormLayout};
