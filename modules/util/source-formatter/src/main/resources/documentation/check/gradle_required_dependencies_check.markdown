## GradleRequiredDependenciesCheck

The
[build.gradle](https://github.com/liferay/liferay-portal/blob/master/modules/apps/static/required-dependencies/required-dependencies/build.gradle)
file in `modules/apps/static/required-dependencies/required-dependencies/`
should only contain dependencies that are used by at least two modules. If a
dependency is used by one or no other module, the dependency should be removed.