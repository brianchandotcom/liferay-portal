## **BND Bundle information** ##

```bnd.bnd``` should always contain the following headers:
```Bundle-Version```, ```Bundle-Name``` and
```Bundle-SymbolicName```

The ```Bundle-Name```, ```Bundle-SymbolicName``` and
```Web-ContextPath``` should match the directory path of the module.

### **Example** ###

```\apps\shopping\shopping-api\bnd.bnd``` should contain the
following ```Bundle-Name``` and ```Bundle-SymbolicName```:

    Bundle-Name: Liferay Shopping API
    Bundle-SymbolicName: com.liferay.shopping.api

---

For web modules (modules ending with ```-web```), the
```Web-ContextPath``` should also match the directory path of the module.

### **Example** ###

```\apps\shopping\shopping-web\bnd.bnd``` contains the following
```Web-ContextPath```:

    Web-ContextPath: /shopping-web