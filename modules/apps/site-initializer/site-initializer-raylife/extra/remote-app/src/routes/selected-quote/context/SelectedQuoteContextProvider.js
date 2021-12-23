import {createContext, useEffect, useReducer} from 'react';
import {STORAGE_KEYS, Storage} from '../../../common/services/liferay/storage';
import {getQuoteComparisonById} from '../../quote-comparison/service/QuoteComparison';
import {getChannel} from '../services/Channel';
import {getSku} from '../services/Product';

export const SelectedQuoteContext = createContext();

const productId = Storage.getItem(STORAGE_KEYS.PRODUCT_ID);

export const ACTIONS = {
	SET_ACCOUNT_ID: 'SET_ACCOUNT_ID',
	SET_COMMERCE: 'SET_COMMERCE',
	SET_EXPANDED: 'SET_EXPANDED',
	SET_ORDER_ID: 'SET_ORDER_ID',
	SET_PANEL: 'SET_PANEL',
	SET_PRODUCT: 'SET_PRODUCT',
	SET_SECTIONS: 'SET_SECTIONS',
	SET_STEP_CHECKED: 'SET_STEP_CHECKED',
};

const initialState = {
	accountId: 0,
	commerce: {
		channel: {},
		skus: [],
	},
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
		case ACTIONS.SET_COMMERCE: {
			return {
				...state,
				commerce: action.payload,
			};
		}

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

	const getInitialData = async () => {
		const {
			basics: {productQuote},
		} = JSON.parse(Storage.getItem(STORAGE_KEYS.APPLICATION_FORM));

		const [channel, sku, product] = await Promise.allSettled([
			getChannel(),
			getSku(productQuote),
			getQuoteComparisonById(productId),
		]);

		dispatch({
			payload: {
				channel: channel.value.data.items[0],
				skus: sku.value.data.items,
			},
			type: ACTIONS.SET_COMMERCE,
		});

		dispatch({
			payload: {...product.value, mostPopular: true},
			type: ACTIONS.SET_PRODUCT,
		});
	};

	useEffect(() => {
		getInitialData();
	}, []);

	return (
		<SelectedQuoteContext.Provider value={[state, dispatch]}>
			{children}
		</SelectedQuoteContext.Provider>
	);
};

export default SelectedQuoteContextProvider;
