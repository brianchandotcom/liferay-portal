export const actionTypes = {
    UPDATE_ASSETS_PATH: 'UPDATE_ASSETS_PATH',
};

const reducer = (state, action) => {
    if (!action) {
        return state;
    }

    switch (action.type) {
        case actionTypes.UPDATE_ASSETS_PATH: {
            return {
                ...state,
                assetsPath: action.payload,
            };
        }
        default: {
            return state;
        }
    }
};

export default reducer;