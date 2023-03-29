@spira_Search @Search @Accounts @List @team_FARO @priority_4
Feature: Search an Account's Individuals
	As an Business User, I should be able to search for Individuals associated with an Account

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I search for "Schneider and Sons"
		* I click "Schneider and Sons" in the table
		* I click the "Individuals" tab

	Scenario: Search an Account's Segments

		# No individuals

		When I search for "Unassociated Individual - ${Random.1}"
		Then I should see a message that there are no "Individuals" found
	
		# Lower case name

		When I search for "maria rau"
		Then I should only see an Individual named "Maria Rau" in the table

		# Upper case name

		When I search for "SANTINA Jakubowski"
		Then I should only see an Individual named "Santina Jakubowski" in the table

		# First name

		When I search for "Willa"
		Then I should only see an Individual named "Willa Watsica" in the table

		# Last name

		When I search for "Boyle"
		Then I should only see an Individual named "Ronna Boyle" in the table

		# Email

		When I search for "Sally.Johns@hotmail.com"
		Then I should only see an Individual named "Sally Johns" in the table

		# Full name

		When I search for "Lavina Waelchi"
		Then I should only see an Individual named "Lavina Waelchi" in the table