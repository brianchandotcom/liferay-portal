const actionType = (preffix, types) => Object.fromEntries(
    types.map(type => [type, `${preffix}_${type}`])
);

const action = (type, payload) => {
    return {
        type,
        payload
    };
}

export { action, actionType };