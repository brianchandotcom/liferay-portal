import {
    actionDefinition
} from '../actions/folder.es'

export const initialState = {
    items: null,
    brands: null,
    loading: null
}

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case actionDefinition.GET_FOLDER_FULFILLED:
            return {
                ...state,
                items: action.payload.data.items,
                brands: action.payload.data.brands,
                loading: false
            }
        case actionDefinition.GET_FOLDER_PENDING:
            return {
                ...state,
                loading: true
            }
        default:
            return state
    }
};