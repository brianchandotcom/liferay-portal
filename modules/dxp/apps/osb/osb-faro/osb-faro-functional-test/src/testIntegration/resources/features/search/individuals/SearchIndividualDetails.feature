@spira_Search @Search @Individuals @List @team_FARO @priority_3
Feature: Search an Individual's Details
	As an Business User, I should be able to search an Individual's Details

	Background: [Setup] Go to an Individual's Details page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab
		* I search for "Alena Will"
		* I click "Alena Will" in the table
		* I click the "Details" tab

	Scenario: Search an Individual's Details

		#	No results

		When I search for "some invalid query"
		Then I should see a message that there are no "items" found

		#	Upper case

		When I search for "PhD"
		Then I should see the following rows in the Contact Details tab
			| suffix | PhD | suffix | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| gender | female | gender | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Lower case

		When I search for "female"
		Then I should see the following rows in the Contact Details tab
			| gender | female | gender | LIFERAY-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| suffix | PhD | suffix | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Property name

		When I search for "additionalName"
		Then I should see the following rows in the Contact Details tab
			| additionalName | Mitchel | middleName | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| fullName | Alena WIll | fullName | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Data source

		When I search for "SALESFORCE-DATASOURCE-FARO-EXAMPLE"
		Then I should see the following rows in the Contact Details tab
			| fullName   | Alena Will           | fullName   | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| city       | Vanettaview          | city       | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| country    | Tonga                | country    | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
			| department | Games, Garden & Kids | department | SALESFORCE-DATASOURCE-FARO-EXAMPLE |

		#	Source name

		When I search for "middleName"
		Then I should see the following rows in the Contact Details tab
			| additionalName | Mitchel | middleName | SALESFORCE-DATASOURCE-FARO-EXAMPLE |
		And I should not see the following rows in the Contact Details tab
			| fullName | Alena Will | fullName | SALESFORCE-DATASOURCE-FARO-EXAMPLE |