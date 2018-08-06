function getFieldsFromModules(modules, dependencies = []) {
    const ModuleKey = Object.keys(modules);

    return ModuleKey.filter((key) => {
        const Module = modules[key];

        return Module.dependencies.find((element) => {
            return element.includes(...dependencies);
        });
    });
};

function FieldsLoader(callback, modules, dependencies = []) {
    const maps = getFieldsFromModules(modules, dependencies);

    if (maps.length > 0) {
        Liferay.Loader.require(...maps, callback);

        return true;
    } else {
        callback();
        return false;
    }
};

export default FieldsLoader;
export {
    FieldsLoader,
    getFieldsFromModules
}