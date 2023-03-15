@spira_Search @Search @Individuals @List @team_FARO @priority_3
Feature: Search for an Individual in the Individual's List
	As an Business User, I should be able to search for an individual in the individual's list by name

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Search for an Individual
#	Scenario: Search for an Individual Using a First Name
		When I search for "Curt"
		Then I should only see Individuals named "Curt" in the table

#	Scenario: Search for an Individual Using a Last Name
		When I search for "Parker"
		Then I should only see Individuals named "Parker" in the table

#	Scenario: Search for an Individual Using a Full Name
		When I search for "Maria Rau"
		Then I should only see Individuals named "Maria Rau" in the table

#	Scenario: Search for an Individual Using an Email
		When I search for "Randy.Smith@gmail.com"
		Then I should only see Individuals named "Randy Smith" in the table

#	Scenario: Search for an Individual Using a Job Title
		When I search for "Global Division Assistant"
		Then I should only see Individuals named "Karlene Sipes" in the table

#	Scenario: Search for an Individual Using the "worksFor" value
		When I search for "Quigley-Larkin"
		Then I should see Individual named "Kylee Blanda" in the table
