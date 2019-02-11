const graphqlClient = {
	callbackReset: null,
	data: {},
	onResetStore(callback) {
		graphqlClient.callbackReset = callback;
		return graphqlClient.unsub;
	},
	query: () => {
		return new Promise(success => success({data: graphqlClient.data}));
	},
	resetStore() {
		if (graphqlClient.callbackReset) {
			graphqlClient.callbackReset();
		}
	},
	unsub() {
		graphqlClient.callbackReset = null;
	}
};

export default graphqlClient;