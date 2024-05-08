let handlers = [];

process.on('exit', invokeHandlers);
process.on('SIGINT', invokeHandlers);
process.on('SIGTERM', invokeHandlers);

export default function onExit(handler) {
	handlers.push(handler);
}

function invokeHandlers() {
	for (const handler of handlers) {
		try {
			handler();
		}
		catch(error) {
			console.error('Exception thrown while running onExit handler:', error);
		}
	}

	handlers = [];	
}

