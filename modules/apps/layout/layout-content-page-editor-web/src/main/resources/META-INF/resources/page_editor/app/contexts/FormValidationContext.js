/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useState} from 'react';

const INITIAL_STATE = {
	setValidations: () => {},
	validations: [],
};

const FormValidationContext = React.createContext(INITIAL_STATE);

const FormValidationContextProvider = ({children}) => {
	const [validations, setValidations] = useState([]);

	return (
		<FormValidationContext.Provider value={{setValidations, validations}}>
			{children}
		</FormValidationContext.Provider>
	);
};

const useFormValidations = () => {
	const {validations} = useContext(FormValidationContext);

	return validations;
};

const useSetFormValidations = () => {
	const {setValidations} = useContext(FormValidationContext);

	return setValidations;
};

export {
	FormValidationContextProvider,
	useFormValidations,
	useSetFormValidations,
};
