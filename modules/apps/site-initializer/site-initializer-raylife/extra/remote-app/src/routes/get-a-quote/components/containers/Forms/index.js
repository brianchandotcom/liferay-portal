/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
