## **Collections** ##

To avoid creating unnecessary overhead, we want to avoid
using```Collection.addAll(Arrays.asList(T...))``` when adding all
elements of an array to a collection.

Instead we should use ```Collections.addAll(Collection, T...)```

### **Example** ###

Incorrect:

```java
List<Class<?>> declaredClasses = new ArrayList<>();

declaredClasses.addAll(Arrays.asList(ClassA.class.getDeclaredClasses()));
declaredClasses.addAll(Arrays.asList(ClassB.class.getDeclaredClasses()));
```

Correct:

```java
List<Class<?>> declaredClasses = new ArrayList<>();

Collections.addAll(declaredClasses, ClassA.class.getDeclaredClasses());
Collections.addAll(declaredClasses, ClassB.class.getDeclaredClasses());
```