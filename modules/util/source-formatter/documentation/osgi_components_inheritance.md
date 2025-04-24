## OSGi Components Inheritance

We should avoid duplicate methods with the `@Reference` annotation in a
`@Component` class and its superclass by using inheritance. We can remove the
`@Reference` method in the class while adding `-dsannotations-options: inherit`
to the `bnd.bnd` in the module.