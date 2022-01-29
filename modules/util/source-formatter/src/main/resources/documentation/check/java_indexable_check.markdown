## JavaIndexableCheck

The `com.liferay.portal.kernel.search.Indexable` is read by the IndexableAdvice
which either updates or deletes the search index for the return type of the
method if there is an indexer registered for the type.

Because IndexableAdvice uses the return type it is never correct to have a void
return type on a method annotated with `@Indexable`.

#### Example

Incorrect:

```java
@Indexable(type = IndexableType.REINDEX)
@Override
public void updateFoo(Foo foo) {

    ...

}

```

Correct:

```java
@Indexable(type = IndexableType.REINDEX)
@Override
public Foo updateFoo(Foo foo) {

    ...

}

```