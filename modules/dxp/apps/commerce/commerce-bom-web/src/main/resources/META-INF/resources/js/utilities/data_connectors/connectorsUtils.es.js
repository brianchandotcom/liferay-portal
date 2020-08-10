export const connectors = new Map();
export const connections = new Map();

export function getConnectorById(connectorId) {
    return connectors.get(connectorId)
}

export function getEmittersValues(id) {

    const emitters = Array.from(connections.keys())

    const connectedEmittersIds = emitters.reduce(
        (connectedEmitters, emitterId) => {
            return connections.get(emitterId).includes(id)
                ? connectedEmitters.concat(emitterId)
                : connectedEmitters
        }, []
    )
    
    return connectedEmittersIds.reduce(
        (acc, id) => ({
            ...acc,
            [id]: connectors.get(id).getValue() || null
        }),
        {}
    )
}

export function emit(id) {
    const listenersId = connections.get(id);

    if(!listenersId) {
        return null;
    }

    const listeners = listenersId.map(
        (listenerId) => connectors.get(listenerId)
    )

    return listeners.map(listener => listener.notified ? listener.notified() :  null)
}

function addConnection(listenerId, emitterId) {
    const addedListeners = connections.get(emitterId) || [];
    connections.set(
        emitterId,
        addedListeners.concat(listenerId)
    )
}

function addConnections(listenerId, connectorsId){
    return connectorsId.map(
        emitterId => addConnection(listenerId, emitterId)
    )
}

export function subscribe(
    id,
    emittersIds,
    getValue,
    notified
) {
    if(emittersIds) {
        addConnections(id, emittersIds)
    }

    return connectors.set(
        id,
        {
            getValue,
            notified
        }
    )
}

export function getStore(){
    return connectors;
}

const dataConnectorOrchestrator = {
    connectors,
    getConnectorById,
    subscribe,
    getStore
}

window.dataConnectorOrchestrator = dataConnectorOrchestrator