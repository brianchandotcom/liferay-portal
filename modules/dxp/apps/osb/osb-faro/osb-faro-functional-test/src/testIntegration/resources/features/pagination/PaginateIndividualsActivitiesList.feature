@spira_Pagination @Pagination @Individuals @List @team_FARO @priority_3
Feature: Individuals Activities list has pagination
  	As a Business User, I should be able to paginate to the second page in individuals activities list

  	Background: [Setup] Navigate to Individuals page
	  	* I go to the "Home" page
	  	* I login as "test@faro.io:test"
	  	* I should see the "Sites" page
	  	* I go to the "Individuals" page

	Scenario: Paginate to the second page of individuals activities page
	  	Given I click the "Known Individuals" tab
	  	And I search for "Tashia Carroll"
	  	And I click "Tashia Carroll" in the table
	  	When I go to page "2" in the creation modal
	  	Then I should be on page "2" in the creation modal
	  	And I should not see an activity named "Visited www.rolanda-lang.com" in the table