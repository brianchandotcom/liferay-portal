## JavaFinderCacheCheck

When a `*FinderImpl.java` contains `public static final FinderPath`, it means that
the class is using `FinderCache` and that we should override the method
`BasePersistenceImpl.fetchByPrimaryKeys(Set<Serializable>)`.