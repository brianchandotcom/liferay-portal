import {
    actionDefinition
} from '../actions/area.es'

export const initialState = {
    highlightedDetail: null,
    imageUrl: null,
    name: null,
    products: [],
    spots: [],
};

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case actionDefinition.HIGHLIGHT_DETAIL:
            return { 
                ...state, 
                highlightedDetail: action.payload
            };
        case actionDefinition.GET_AREA_FULFILLED:
            return {
                ...state,
                imageUrl: action.payload.data.imageUrl,
                name: action.payload.data.name,
                products: (action.payload.data.products ? action.payload.data.products : []),
                spots: ( action.payload.data.spots ? action.payload.data.spots : [])
            }
        default:
            return state
    }
};