function _construct(Parent, args) {
	_construct = function _construct(Parent, args) {
		var a = [null];
		a.push.apply(a, args);
		var Constructor = Function.bind.apply(Parent, a);
		var instance = new Constructor();
		return instance;
	};
return _construct.apply(null, arguments);
}

var context = Object.assign(
	$CONTEXT
);

var componentConfig = {
	destroyOnNavigate: true,
	portletId: context.portletId
};

var componentArgs = [context];

if ($WRAPPER) {
	componentArgs.push('#$ID');
}

if (context.defaultEventHandler) {
	Liferay.componentReady(
		context.defaultEventHandler
	).then(
		function(defaultEventHandler) {
			context.defaultEventHandler = defaultEventHandler;

			Liferay.component(
				'$ID',
				_construct($MODULE.default, componentArgs),
				componentConfig
			);
		}
	);
}
else {
	Liferay.component(
		'$ID',
		_construct($MODULE.default, componentArgs),
		componentConfig
	);
}