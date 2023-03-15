@spira_Pagination @Pagination @List @team_FARO @priority_4
Feature: Table Pagination
	As an Business User, I should be able to see paginated items in an table

	Background: [Setup] Navigate to the Test Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Individuals" in the sidebar
		* I click the "Known Individuals" tab

	Scenario: Assert Table Pagination Delta
		Given I should see the "Individuals" page
		When I set the pagination delta to "75"
		Then I should see "75" rows in the table