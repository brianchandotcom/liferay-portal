import { actionDefinition } from '../actions/app.es'

export const initialState = {
    loading: false,
    error: null,
    spritemap: null,
    areaApiUrl: null,
    productApiUrl: null,
    initialized: false
}

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case actionDefinition.SET_ERROR:
            return {
                ...state,
                error: action.payload 
            }
        case actionDefinition.INITIALIZE_APP_DATA:
            return {
                ...state,
                ...action.payload,
                initialized: true
            }
        case actionDefinition.SET_LOADING:
            return {
                ...state,
                loading: action.payload
            }
        case actionDefinition.SET_SPRITEMAP:
            return {
                ...state,
                spritemap: action.payload
            }
        default:
            return state;
    }
};