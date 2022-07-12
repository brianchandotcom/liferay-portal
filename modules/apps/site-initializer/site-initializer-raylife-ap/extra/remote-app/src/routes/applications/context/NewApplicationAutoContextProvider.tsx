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

import React, {ReactNode, createContext, useReducer} from 'react';

import {ActionMap} from '../../../types';

type ContactInfoFormTypes = {
	apt: string;
	city: string;
	dateOfBirth: string;
	email: string;
	firstName: string;
	lastName: string;
	phone: string;
	state: string;
	streetAddress: string;
	zipCode: string;
};

type CoverageFormTypes = {
	bodilyInjury: string;
	collision: string;
	comprehensive: string;
	medical: string;
	propertyDamage: string;
	uninsuredOrUnderinsuredMBI: string;
	uninsuredOrUnderinsuredMPD: string;
};

type DriverInfoFormTypes = {
	accidentCitation: string;
	ageFirstLicenced: string;
	firstName: string;
	gender: string;
	governmentAffiliation: string;
	hasAccidentOrCitations: boolean;
	highestEducation: string;
	lastName: string;
	maritalStatus: string;
	militaryAffiliation: string;
	ocupation: string;
	otherOcupation: string;
	relationToContact: string;
};

export type VehicleInfoFormTypes = {
	annualMileage: string;
	id: number;
	make: string;
	model: string;
	ownership: string;
	primaryUsage: string;
	year: string;
};

export type InitialStateTypes = {
	currentStep: number;
	hasFormChanges: boolean;
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
			form: DriverInfoFormTypes;
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
	currentStep: 0,
	hasFormChanges: false,
	steps: {
		contactInfo: {
			form: {
				apt: '',
				city: '',
				dateOfBirth: '',
				email: '',
				firstName: '',
				lastName: '',
				phone: '',
				state: '',
				streetAddress: '',
				zipCode: '',
			},
			index: 0,
			name: 'Contact Info',
		},
		coverage: {
			form: {
				bodilyInjury: '',
				collision: '',
				comprehensive: '',
				medical: '',
				propertyDamage: '',
				uninsuredOrUnderinsuredMBI: '',
				uninsuredOrUnderinsuredMPD: '',
			},
			index: 3,
			name: 'Coverage',
		},
		driverInfo: {
			form: {
				accidentCitation: '',
				ageFirstLicenced: '',
				firstName: '',
				gender: '',
				governmentAffiliation: '',
				hasAccidentOrCitations: false,
				highestEducation: '',
				lastName: '',
				maritalStatus: '',
				militaryAffiliation: '',
				ocupation: '',
				otherOcupation: '',
				relationToContact: '',
			},
			index: 2,
			name: 'Driver Info',
		},
		review: {
			index: 4,
			name: 'Review',
		},
		vehicleInfo: {
			form: [
				{
					annualMileage: '',
					id: Number((Math.random() * 1000000).toFixed(0)),
					make: '',
					model: '',
					ownership: '',
					primaryUsage: '',
					year: '',
				},
			],
			index: 1,
			name: 'Vehicle Info',
		},
	},
};

export enum ACTIONS {
	SET_CURRENT_STEP = 'SET_CURRENT_STEP',
	SET_CONTACT_INFO_FORM = 'SET_CONTACT_INFO_FORM',
	SET_VEHICLE_INFO_FORM = 'SET_VEHICLE_INFO_FORM',
	SET_COVERAGE_FORM = 'SET_COVERAGE_FORM',
	SET_DRIVER_INFO_FORM = 'SET_DRIVER_INFO_FORM',
	SET_HAS_FORM_CHANGE = 'SET_HAS_FORM_CHANGE',
	SET_NEW_VEHICLE = 'SET_NEW_VEHICLE',
	SET_REMOVE_VEHICLE = 'SET_REMOVE_VEHICLE',
}

type ActionsPayload = {
	[ACTIONS.SET_CONTACT_INFO_FORM]: ContactInfoFormTypes;
	[ACTIONS.SET_COVERAGE_FORM]: CoverageFormTypes;
	[ACTIONS.SET_CURRENT_STEP]: number;
	[ACTIONS.SET_DRIVER_INFO_FORM]: DriverInfoFormTypes;
	[ACTIONS.SET_HAS_FORM_CHANGE]: boolean;
	[ACTIONS.SET_NEW_VEHICLE]: VehicleInfoFormTypes;
	[ACTIONS.SET_REMOVE_VEHICLE]: {id: number};
	[ACTIONS.SET_VEHICLE_INFO_FORM]: {
		fieldName: string;
		id: number;
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
			return {
				...state,
				steps: {
					...state.steps,
					driverInfo: {
						...state.steps.driverInfo,
						form: action.payload,
					},
				},
			};
		}

		case ACTIONS.SET_NEW_VEHICLE: {
			const payload = state.steps.vehicleInfo.form;

			payload.push(action.payload);

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

		case ACTIONS.SET_REMOVE_VEHICLE: {
			const id = action.payload.id;
			const forms = state.steps.vehicleInfo.form;

			const payload = forms.filter((form) => form.id !== id);

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

		default: {
			return state;
		}
	}
};

export type NewApplicationAutoProviderProps = Partial<InitialStateTypes>;

const NewApplicationAutoContextProvider: React.FC<
	NewApplicationAutoProviderProps & {children: ReactNode}
> = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<NewApplicationAutoContext.Provider value={[state, dispatch]}>
			{children}
		</NewApplicationAutoContext.Provider>
	);
};

export default NewApplicationAutoContextProvider;
