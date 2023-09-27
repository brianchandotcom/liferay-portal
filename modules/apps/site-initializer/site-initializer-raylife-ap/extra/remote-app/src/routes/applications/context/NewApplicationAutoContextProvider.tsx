/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, createContext, useEffect, useReducer} from 'react';

import {getApplicationByExternalReferenceCode} from '../../../common/services';
import {ActionMap} from '../../../types';

export type ContactInfoFormTypes = {
	apt: string;
	city: string;
	dateOfBirth: string;
	email: string;
	firstName: string;
	lastName: string;
	ownership: string;
	phone: string;
	state: string;
	streetAddress: string;
	zipCode: string;
};

const ContactInfoForm: ContactInfoFormTypes = {
	apt: '',
	city: '',
	dateOfBirth: '',
	email: '',
	firstName: '',
	lastName: '',
	ownership: '',
	phone: '',
	state: '',
	streetAddress: '',
	zipCode: '',
};

export type CoverageFormTypes = {
	bodilyInjury: string;
	medical: string;
	propertyDamage: string;
	uninsuredOrUnderinsuredMBI: string;
	uninsuredOrUnderinsuredMPD: string;
	vehicles: {
		collision: string;
		comprehensive: string;
	}[];
};

const CoverageForm: CoverageFormTypes = {
	bodilyInjury: '',
	medical: '',
	propertyDamage: '',
	uninsuredOrUnderinsuredMBI: '',
	uninsuredOrUnderinsuredMPD: '',
	vehicles: [
		{
			collision: '',
			comprehensive: '',
		},
	],
};

type AccidentCitationTypes = {
	id: number;
	value: string;
};

export type DriverInfoFormTypes = {
	accidentCitation: AccidentCitationTypes[];
	ageFirstLicenced: string;
	firstName: string;
	gender: string;
	governmentAffiliation: string;
	hasAccidentOrCitations: string;
	highestEducation: string;
	id: number;
	lastName: string;
	maritalStatus: string;
	millitaryAffiliation: string;
	occupation: string;
	otherOccupation: string;
	relationToContact: string;
};

const DriverInfoForm: DriverInfoFormTypes[] = [
	{
		accidentCitation: [],
		ageFirstLicenced: '',
		firstName: '',
		gender: '',
		governmentAffiliation: '',
		hasAccidentOrCitations: '',
		highestEducation: '',
		id: Number((Math.random() * 1000000).toFixed(0)),
		lastName: '',
		maritalStatus: '',
		millitaryAffiliation: '',
		occupation: '',
		otherOccupation: '',
		relationToContact: '',
	},
];

export type VehicleInfoFormTypes = {
	annualMileage: string;
	id: number;
	make: string;
	model: string;
	ownership: string;
	primaryUsage: string;
	year: string;
};

const VehicleInfoForm: VehicleInfoFormTypes[] = [
	{
		annualMileage: '',
		id: Number((Math.random() * 1000000).toFixed(0)),
		make: '',
		model: '',
		ownership: '',
		primaryUsage: '',
		year: '',
	},
];

export type InitialStateTypes = {
	applicationId: string;
	currentStep: number;
	externalReferenceCode: string;
	hasFormChanges: boolean;
	isAbleToBeSave: boolean;
	isAbleToNextStep: boolean;
	steps: {
		contactInfo: {
			form: ContactInfoFormTypes;
			index: number;
			name: string;
		};
		coverage: {
			form: CoverageFormTypes;
			index: number;
			name: string;
		};
		driverInfo: {
			form: DriverInfoFormTypes[];
			index: number;
			name: string;
		};
		review: {
			index: number;
			name: string;
		};
		vehicleInfo: {
			form: VehicleInfoFormTypes[];
			index: number;
			name: string;
		};
	};
};

const initialState: InitialStateTypes = {
	applicationId: '',
	currentStep: 0,
	externalReferenceCode: '',
	hasFormChanges: false,
	isAbleToBeSave: false,
	isAbleToNextStep: false,
	steps: {
		contactInfo: {
			form: ContactInfoForm,
			index: 0,
			name: 'Contact Info',
		},
		coverage: {
			form: CoverageForm,
			index: 3,
			name: 'Coverage',
		},
		driverInfo: {
			form: DriverInfoForm,
			index: 2,
			name: 'Driver Info',
		},
		review: {
			index: 4,
			name: 'Review',
		},
		vehicleInfo: {
			form: VehicleInfoForm,
			index: 1,
			name: 'Vehicle Info',
		},
	},
};

export enum ACTIONS {
	SET_APPLICATION = 'SET_APPLICATION',
	SET_CURRENT_STEP = 'SET_CURRENT_STEP',
	SET_CONTACT_INFO_FORM = 'SET_CONTACT_INFO_FORM',
	SET_VEHICLE_INFO_FORM = 'SET_VEHICLE_INFO_FORM',
	SET_COVERAGE_FORM = 'SET_COVERAGE_FORM',
	SET_DRIVER_INFO_FORM = 'SET_DRIVER_INFO_FORM',
	SET_INITIAL_STATE = 'SET_INITIAL_STATE',
	SET_HAS_FORM_CHANGE = 'SET_HAS_FORM_CHANGE',
	SET_IS_ABLE_TO_NEXT = 'SET_IS_ABLE_TO_NEXT',
	SET_IS_ABLE_TO_SAVE = 'SET_IS_ABLE_TO_SAVE',
	SET_NEW_VEHICLE = 'SET_NEW_VEHICLE',
	SET_NEW_DRIVER = 'SET_NEW_DRIVER',
	SET_NEW_ACCIDENT_CITATION = 'SET_NEW_ACCIDENT_CITATION',
	SET_REMOVE_ACCIDENT_CITATION = 'SET_REMOVE_ACCIDENT_CITATION',
	SET_REMOVE_DRIVER = 'SET_REMOVE_DRIVER',
	SET_REMOVE_VEHICLE = 'SET_REMOVE_VEHICLE',
	UPDATE_DRIVER_INFO_FORM = 'UPDATE_DRIVER_INFO_FORM',
}

type ActionsPayload = {
	[ACTIONS.SET_APPLICATION]: {externalReferenceCode: string; id: number};
	[ACTIONS.SET_CONTACT_INFO_FORM]: ContactInfoFormTypes;
	[ACTIONS.SET_COVERAGE_FORM]: CoverageFormTypes;
	[ACTIONS.SET_CURRENT_STEP]: number;
	[ACTIONS.SET_DRIVER_INFO_FORM]: {
		fieldName: string;
		id: number;
		value: string;
	};
	[ACTIONS.SET_HAS_FORM_CHANGE]: boolean;
	[ACTIONS.SET_INITIAL_STATE]: InitialStateTypes;
	[ACTIONS.SET_IS_ABLE_TO_NEXT]: boolean;
	[ACTIONS.SET_IS_ABLE_TO_SAVE]: boolean;
	[ACTIONS.SET_NEW_ACCIDENT_CITATION]: DriverInfoFormTypes;
	[ACTIONS.SET_NEW_DRIVER]: DriverInfoFormTypes;
	[ACTIONS.SET_NEW_VEHICLE]: VehicleInfoFormTypes;
	[ACTIONS.SET_REMOVE_ACCIDENT_CITATION]: {
		accidentCitationIndex: number;
		formIndex: number;
	};
	[ACTIONS.SET_REMOVE_DRIVER]: {id: number};
	[ACTIONS.SET_REMOVE_VEHICLE]: {id: number};
	[ACTIONS.SET_VEHICLE_INFO_FORM]: {
		fieldName: string;
		id: number;
		value: string;
	};
	[ACTIONS.UPDATE_DRIVER_INFO_FORM]: {
		formId: number;
		id: number;
		index: number;
		value: string;
	};
};

type ApplicationActions = ActionMap<ActionsPayload>[keyof ActionMap<
	ActionsPayload
>];

export const NewApplicationAutoContext = createContext<
	[InitialStateTypes, (param: ApplicationActions) => void]
>([initialState, () => null]);

const reducer = (state: InitialStateTypes, action: ApplicationActions) => {
	switch (action.type) {
		case ACTIONS.SET_APPLICATION: {
			return {
				...state,
				applicationId: action.payload.id.toString(),
				externalReferenceCode: action.payload.externalReferenceCode,
			};
		}

		case ACTIONS.SET_CURRENT_STEP: {
			return {
				...state,
				currentStep: action.payload,
			};
		}

		case ACTIONS.SET_HAS_FORM_CHANGE: {
			return {
				...state,
				hasFormChanges: action.payload,
			};
		}
		case ACTIONS.SET_IS_ABLE_TO_NEXT: {
			return {
				...state,
				isAbleToNextStep: action.payload,
			};
		}

		case ACTIONS.SET_IS_ABLE_TO_SAVE: {
			return {
				...state,
				isAbleToBeSave: action.payload,
			};
		}

		case ACTIONS.SET_CONTACT_INFO_FORM: {
			return {
				...state,
				steps: {
					...state.steps,
					contactInfo: {
						...state.steps.contactInfo,
						form: action.payload,
					},
				},
			};
		}

		case ACTIONS.SET_VEHICLE_INFO_FORM: {
			const payload = state.steps.vehicleInfo.form.map((currentForm) => {
				if (currentForm.id === action.payload.id) {
					return {
						...currentForm,
						[action.payload.fieldName]: action.payload.value,
					};
				}

				return currentForm;
			});

			return {
				...state,
				steps: {
					...state.steps,
					vehicleInfo: {
						...state.steps.vehicleInfo,
						form: payload,
					},
				},
			};
		}

		case ACTIONS.SET_COVERAGE_FORM: {
			return {
				...state,
				steps: {
					...state.steps,
					coverage: {
						...state.steps.coverage,
						form: action.payload,
					},
				},
			};
		}

		case ACTIONS.SET_DRIVER_INFO_FORM: {
			const payload = state.steps.driverInfo.form.map((currentForm) => {
				if (currentForm.id === action.payload.id) {
					return {
						...currentForm,
						[action.payload.fieldName]: action.payload.value,
					};
				}

				return currentForm;
			});

			return {
				...state,
				steps: {
					...state.steps,
					driverInfo: {
						...state.steps.driverInfo,
						form: payload,
					},
				},
			};
		}

		case ACTIONS.UPDATE_DRIVER_INFO_FORM: {
			const formId = action?.payload?.formId;
			const index = action?.payload?.index;
			const id = action?.payload?.id;
			const value = action?.payload?.value;
			const forms = state?.steps?.driverInfo?.form;

			const payload = forms[index].accidentCitation.map(
				(currentAccidentCitation) => {
					if (currentAccidentCitation.id === id) {
						return {
							...currentAccidentCitation,
							value,
						};
					}

					return currentAccidentCitation;
				}
			);

			const newPayload = forms.map((currentForm) => {
				if (currentForm.id === formId) {
					return {
						...currentForm,
						accidentCitation: payload,
					};
				}

				return currentForm;
			});

			return {
				...state,
				steps: {
					...state.steps,
					driverInfo: {
						...state.steps.driverInfo,
						form: newPayload,
					},
				},
			};
		}

		case ACTIONS.SET_NEW_DRIVER: {
			const payload = state.steps.driverInfo.form;

			payload.push(action.payload);

			return {
				...state,
				steps: {
					...state.steps,
					driverInfo: {
						...state.steps.driverInfo,
						form: payload,
					},
				},
			};
		}

		case ACTIONS.SET_REMOVE_DRIVER: {
			const id = action.payload.id;
			const forms = state.steps.driverInfo.form;

			const payload = forms.filter((form) => form.id !== id);

			return {
				...state,
				steps: {
					...state.steps,
					driverInfo: {
						...state.steps.driverInfo,
						form: payload,
					},
				},
			};
		}

		case ACTIONS.SET_NEW_ACCIDENT_CITATION: {
			const payload = state?.steps?.driverInfo?.form;
			const formId = action?.payload?.id;

			const forms = payload.filter((form) => form.id === formId);

			forms.forEach((form) =>
				form.accidentCitation.push(action.payload.accidentCitation[0])
			);

			return {
				...state,
				steps: {
					...state.steps,
					driverInfo: {
						...state.steps.driverInfo,
						form: payload,
					},
				},
			};
		}

		case ACTIONS.SET_REMOVE_ACCIDENT_CITATION: {
			const payload = state?.steps?.driverInfo?.form;

			const accidentCitationIndex =
				action?.payload?.accidentCitationIndex;

			const formIndex = action?.payload?.formIndex;

			const formId = payload[formIndex].id;

			const accidentCitationId =
				payload[formIndex].accidentCitation[accidentCitationIndex].id;

			const accidentCitationFiltered = payload[
				formIndex
			].accidentCitation.filter(
				(currentAccidentCitation) =>
					currentAccidentCitation.id !== accidentCitationId
			);

			const newPayload = payload.map((currentForm) => {
				if (currentForm.id === formId) {
					return {
						...currentForm,
						accidentCitation: accidentCitationFiltered,
					};
				}

				return currentForm;
			});

			return {
				...state,
				steps: {
					...state.steps,
					driverInfo: {
						...state.steps.driverInfo,
						form: newPayload,
					},
				},
			};
		}

		case ACTIONS.SET_NEW_VEHICLE: {
			return {
				...state,
				steps: {
					...state.steps,
					vehicleInfo: {
						...state.steps.vehicleInfo,
						form: [...state.steps.vehicleInfo.form, action.payload],
					},
				},
			};
		}

		case ACTIONS.SET_REMOVE_VEHICLE: {
			const id = action.payload.id;
			const forms = state.steps.vehicleInfo.form;

			const payload = forms.filter((_, index) => id !== index);

			return {
				...state,
				steps: {
					...state.steps,
					coverage: {
						...state.steps.coverage,
						form: {
							...state.steps.coverage.form,
							vehicles: state.steps.coverage.form.vehicles.filter(
								(_, index) => index !== id
							),
						},
					},
					vehicleInfo: {
						...state.steps.vehicleInfo,
						form: payload,
					},
				},
			};
		}

		case ACTIONS.SET_INITIAL_STATE: {
			return action.payload;
		}

		default: {
			return state;
		}
	}
};

export type NewApplicationAutoProviderProps = Partial<InitialStateTypes>;

const NewApplicationAutoContextProvider: React.FC<
	NewApplicationAutoProviderProps & {
		children: ReactNode;
		defaultState?: Partial<InitialStateTypes>;
	}
> = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	const getInitialState = async () => {
		const queryParams = new URLSearchParams(window.location.search);
		const externalReferenceCode = queryParams.get('externalReferenceCode');

		const {data = {}} = await getApplicationByExternalReferenceCode(
			externalReferenceCode || ''
		);

		const dataJSON = data.dataJSON ? JSON.parse(data.dataJSON) : {};

		const payload = {
			applicationId: data.id,
			currentStep: 0,
			externalReferenceCode: data.externalReferenceCode,
			hasFormChanges: false,
			isAbleToBeSave: false,
			isAbleToNextStep: true,
			steps: {
				contactInfo: {
					form: {
						apt: data.addressApt || '',
						city: data.city || '',
						dateOfBirth: dataJSON.contactInfo?.dateOfBirth || '',
						email: data.email || '',
						firstName: data.firstName || '',
						lastName: data.lastName || '',
						ownership: dataJSON.contactInfo?.ownership || '',
						phone: data.phone || '',
						state: data.state || '',
						streetAddress: data.address || '',
						zipCode: data.zip || '',
					},
					index: 0,
					name: 'Contact Info',
				},
				coverage: {
					form: dataJSON.coverage?.form
						? dataJSON.coverage?.form
						: CoverageForm,
					index: 3,
					name: 'Coverage',
				},
				driverInfo: {
					form: dataJSON.driverInfo?.form
						? dataJSON.driverInfo?.form
						: DriverInfoForm,
					index: 2,
					name: 'Driver Info',
				},
				review: {
					index: 4,
					name: 'Review',
				},
				vehicleInfo: {
					form: dataJSON.vehicleInfo?.form
						? dataJSON.vehicleInfo?.form
						: VehicleInfoForm,
					index: 1,
					name: 'Vehicle Info',
				},
			},
		};

		return payload;
	};

	useEffect(() => {
		getInitialState().then((response) => {
			dispatch({payload: response, type: ACTIONS.SET_INITIAL_STATE});
		});
	}, []);

	return (
		<NewApplicationAutoContext.Provider value={[state, dispatch]}>
			{children}
		</NewApplicationAutoContext.Provider>
	);
};

export default NewApplicationAutoContextProvider;
