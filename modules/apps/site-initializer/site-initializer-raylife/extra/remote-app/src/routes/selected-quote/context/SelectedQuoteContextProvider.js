import {createContext, useEffect, useReducer} from 'react';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {getQuoteComparisonById} from '../../quote-comparison/service/QuoteComparison';

export const SelectedQuoteContext = createContext();

const productId = Storage.getItem(STORAGE_KEYS.PRODUCT_ID);

export const ACTIONS = {
	SET_ACCOUNT_ID: 'SET_ACCOUNT_ID',
	SET_EXPANDED: 'SET_EXPANDED',
	SET_ORDER_ID: 'SET_ORDER_ID',
	SET_PANEL: 'SET_PANEL',
	SET_PRODUCT: 'SET_PRODUCT',
	SET_SECTIONS: 'SET_SECTIONS',
	SET_STEP_CHECKED: 'SET_STEP_CHECKED',
};

const initialState = {
	accountId: 0,
	orderId: 0,
	panel: {
		createAnAccount: {
			checked: false,
			expanded: true,
		},
		selectPaymentMethod: {
			checked: false,
			expanded: false,
		},
		uploadDocuments: {
			checked: false,
			expanded: false,
		},
	},
	product: {},
	sections: [
		{
			error: false,
			errorMessage: 'Please upload a copy of your business license.',
			files: [],
			required: true,
			sectionId: null,
			subtitle: 'Upload a copy of your business license.',
			title: 'Business License',
			type: 'document',
		},
		{
			error: false,
			files: [],
			required: false,
			sectionId: null,
			subtitle: 'Upload a copy of your additional documents.',
			title: 'Additional Documents',
			type: 'document',
		},
		{
			error: false,
			errorMessage: 'Please upload a photo of your building interior.',
			files: [],
			required: true,
			sectionId: null,
			subtitle: 'Upload at least 1 photo of your building interior.',
			title: 'Building Interior Photos',
			type: 'image',
		},
	],
};

const SelectedQuoteReducer = (state = initialState, action) => {
	const setPanel = (panelKey, panelKeyProperty, value) => {
		const newPanel = {...state.panel};

		newPanel[panelKey][panelKeyProperty] =
			value ?? !newPanel[panelKey][panelKeyProperty];

		return newPanel;
	};

	switch (action.type) {
		case ACTIONS.SET_EXPANDED: {
			return {
				...state,
				panel: setPanel(
					action.payload.panelKey,
					'expanded',
					action.payload.value
				),
			};
		}

		case ACTIONS.SET_STEP_CHECKED: {
			const panel = setPanel(
				action.payload.panelKey,
				'checked',
				action.payload.value
			);

			return {
				...state,
				panel,
			};
		}

		case ACTIONS.SET_PRODUCT: {
			return {
				...state,
				product: action.payload,
			};
		}

		case ACTIONS.SET_ACCOUNT_ID: {
			return {
				...state,
				accountId: action.payload,
			};
		}

		case ACTIONS.SET_SECTIONS: {
			return {
				...state,
				sections: action.payload,
			};
		}

		case ACTIONS.SET_ORDER_ID: {
			return {
				...state,
				orderId: action.payload,
			};
		}

		default: {
			return state;
		}
	}
};

const SelectedQuoteContextProvider = ({children}) => {
	const [state, dispatch] = useReducer(SelectedQuoteReducer, initialState);

	useEffect(() => {
		getQuoteComparisonById(productId)
			.then((product) => {
				dispatch({
					payload: {...product, mostPopular: true},
					type: ACTIONS.SET_PRODUCT,
				});
			})
			.catch((error) => console.error(error.message));
	}, []);

	return (
		<SelectedQuoteContext.Provider value={[state, dispatch]}>
			{children}
		</SelectedQuoteContext.Provider>
	);
};

export default SelectedQuoteContextProvider;
