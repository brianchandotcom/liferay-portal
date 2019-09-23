## Imports

### Do Not Use Wildcards

#### Example

Incorrect:

```java
import com.liferay.portal.kernel.util.*;
```

Correct:

```java
import com.liferay.portal.kernel.util.StringComparator;
import com.liferay.portal.kernel.util.StringParser;
import com.liferay.portal.kernel.util.StringUtil;
```

### Import Classes, Not Methods

#### Example

Incorrect:

```java
import static org.junit.Assert.assertTrue;

...

assertTrue(user.isActive());
```

Correct:

```java
import org.junit.Assert;

...

Assert.assertTrue(user.isActive());
```

### Do Not Import Constants

Avoid importing constants, instead import the class.

#### Example

Incorrect:

```java
import static com.liferay.portal.kernel.util.ActionKeys.UPDATE;

...

doSomething(UPDATE);
```

Correct:

```java
import com.liferay.portal.kernel.util.ActionKeys;

...

doSomething(ActionKeys.UPDATE);
```

In case there is already an imported class with the same name, use the Fully
Qualified Name instead.

#### Example

Incorrect:

```java
import static com.liferay.portal.kernel.util.ActionKeys.UPDATE;

import com.liferay.portal.util.ActionKeys;
...

doSomething(UPDATE);
```

Correct:

```java
import com.liferay.portal.util.ActionKeys;

...

doSomething(com.liferay.portal.kernel.util.ActionKeys.UPDATE);
```