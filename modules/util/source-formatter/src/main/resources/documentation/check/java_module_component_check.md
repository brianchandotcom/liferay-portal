## JavaModuleComponentCheck

Do not create `@Component` classes in `-api` or `-spi` modules. All `@Component`
classes contain implementation details which do not belong in any API. Move
`@Component` classes to modules ending with `-impl`, `-service`, or `-web`. If a
class in the `-api` or `-spi` module cannot function without the `@Component`
class move it to a different module as well. If needed, replace the class with
an interface.