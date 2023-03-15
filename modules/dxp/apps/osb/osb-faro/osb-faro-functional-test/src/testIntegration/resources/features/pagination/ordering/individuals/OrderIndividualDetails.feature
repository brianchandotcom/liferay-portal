@spira_Order @Order @Individuals @List @team_FARO @priority_3
Feature: Order an Individual's Details
	As an Business User, I should be able to order an Individual's Details

	Background: [Setup] Go to an Individual's Details page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab
		* I search for "Alena Will"
		* I click "Alena Will" in the table
		* I click the "Details" tab

	Scenario: Order an Individual's Details

		# Order by Attribute Name

		When I sort the table by the "Attribute" column header in ascending order
		Then I should see the following ordered rows in column 1:
			| additionalName |
			| birthDate      |
			| city           |
			| country        |
			| department     |
			| description    |
			| doNotCall      |
			| email          |
			| familyName     |
			| fax            |
			| fullName       |
			| gender         |
			| givenName      |
			| industry       |
			| jobTitle       |
			| mobilePhone    |
			| postalCode     |
			| state          |
			| street         |
			| suffix         |
			| telephone      |
			| worksFor       |
		When I sort the table by the "Attribute" column header in descending order
		Then I should see the following ordered rows in column 1:
			| worksFor       |
			| telephone      |
			| suffix         |
			| street         |
			| state          |
			| postalCode     |
			| mobilePhone    |
			| jobTitle       |
			| industry       |
			| givenName      |
			| gender         |
			| fullName       |
			| fax            |
			| familyName     |
			| email          |
			| doNotCall      |
			| description    |
			| department     |
			| country        |
			| city           |
			| birthDate      |
			| additionalName |

		# Order by Source Name

		When I sort the table by the "Source Name" column header in ascending order
		Then I should see the following ordered rows in column 2:
			| birthDate   |
			| city        |
			| company     |
			| country     |
			| department  |
			| description |
			| doNotCall   |
			| email       |
			| fax         |
			| firstName   |
			| fullName    |
			| gender      |
			| industry    |
			| lastName    |
			| middleName  |
			| mobilePhone |
			| phone       |
			| postalCode  |
			| state       |
			| street      |
			| suffix      |
			| title       |
		When I sort the table by the "Source Name" column header in descending order
		Then I should see the following ordered rows in column 2:
			| title       |
			| suffix      |
			| street      |
			| state       |
			| postalCode  |
			| phone       |
			| mobilePhone |
			| middleName  |
			| lastName    |
			| industry    |
			| gender      |
			| fullName    |
			| firstName   |
			| fax         |
			| email       |
			| doNotCall   |
			| description |
			| department  |
			| country     |
			| company     |
			| city        |
			| birthDate   |