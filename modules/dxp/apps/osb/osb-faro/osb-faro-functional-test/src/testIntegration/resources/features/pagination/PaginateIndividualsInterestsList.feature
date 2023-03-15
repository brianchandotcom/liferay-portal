@spira_Pagination @Pagination @Individuals @List @team_FARO @priority_3
Feature: Individuals Interest list has pagination
	As a Business User, I should be able to paginate to the second page of individuals interest list

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Paginate to the second page of individuals interest
		Given I click the "Interests" tab
		And I set the pagination delta to "5"
		When I go to page "2" in the table
		Then I should be on page "2" in the table
		And I should see an element exists in the table